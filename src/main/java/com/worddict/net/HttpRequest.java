package com.worddict.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;


/**
 * HTTP client with shared rate-limit handling.
 *
 * <p>Rate-limit state is maintained separately for each {@code host:port}.
 * When a server returns {@link HttpResponse#HTTP_TOO_MANY_REQUESTS}
 * (HTTP 429) with a valid {@code Retry-After} value, subsequent requests
 * to the same host and port are temporarily blocked.</p>
 *
 * <p>While a host is blocked, {@link #get(String)} returns a synthetic
 * HTTP 429 response instead of sending a network request. Requests to
 * other hosts and ports are not affected.</p>
 *
 * <p>This class does not perform retries or wait for a rate limit to expire.
 * The caller is responsible for deciding whether and when to retry, typically
 * by checking {@link #isBlocked(String)} and waiting before calling
 * {@link #get(String)} again.</p>
 *
 * <p>The actual HTTP transport is separated from the rate-limit logic through
 * {@link RequestExecutor}. This allows the transport to be replaced in tests
 * without making real network requests.</p>
 *
 * <p>See {@code HttpRequestTest} for usage examples and
 * {@code Wiktionary.getUrlWithRetry()} for production usage.</p>
 */
public class HttpRequest {
    private static final String USER_AGENT = "WordDict/1.0 (OpenJDK)";
    private static final long EXTRA_DELAY_MS = 1000L;
    private static final Map<String, Long> blockedUntil = new HashMap<>();

    interface RequestExecutor {
        HttpResponse get(String url) throws IOException;
    }

    /**
     * Executes an HTTP GET request using the default HTTP transport.
     *
     * <p>If the shared rate limit is active, no network request is made and
     * a synthetic HTTP 429 response is returned.</p>
     *
     * @param url the URL to request
     * @return the HTTP response
     * @throws IOException if an I/O error occurs
     */
    public static HttpResponse get(String url) throws IOException {
        return get(url, HttpRequest::getUrl);
    }

    /**
     * Executes a GET request using the specified request executor.
     *
     * <p>If the rate limit for the requested host and port is active, the
     * executor is not called and a synthetic HTTP 429 response is returned.
     * When the executor returns HTTP 429 with a positive {@code Retry-After}
     * value, a corresponding block is registered for that host and port.</p>
     *
     * <p>The method itself never waits for the block to expire and never performs
     * automatic retries.</p>
     *
     * @param url the URL to request
     * @param executor transport used to execute the request
     * @return the HTTP response
     * @throws IOException if the request executor fails or the URL is invalid
     */
    public static HttpResponse get(String url, RequestExecutor executor)
            throws IOException {
        String host = getHost(url);
        synchronized (HttpRequest.class) {
            if (isBlockedHost(host)) {
                return new HttpResponse(
                        HttpResponse.HTTP_TOO_MANY_REQUESTS,
                        getRetryAfter(host),
                        "");
            }
        }
        HttpResponse response = executor.get(url);
        synchronized (HttpRequest.class) {
            if (response.isTooManyRequests()) {
                blockedUntil.put(
                        host,
                        System.currentTimeMillis()
                                + response.getRetryAfter() * 1000L
                                + EXTRA_DELAY_MS);
            } else if (response.isOk()) {
                Long until = blockedUntil.get(host);
                if (until != null
                        && System.currentTimeMillis() >= until) {
                    blockedUntil.remove(host);
                }
            }
        }
        return response;
    }

    /**
     * Executes a raw HTTP GET request without rate-limit handling.
     *
     * <p>This method is used internally as the default {@link RequestExecutor}.
     * HTTP error responses such as 404 and 429 are returned as
     * {@link HttpResponse} objects rather than being converted to
     * {@link IOException}.</p>
     *
     * @param urlSpec the URL to fetch
     * @return the HTTP response
     * @throws IOException if the URL is invalid or an I/O error occurs
     */
    static HttpResponse getUrl(String urlSpec) throws IOException {
        URL url;
        try {
            url = new URL(urlSpec);
        } catch (MalformedURLException e) {
            throw new IOException("Invalid URL format: " + urlSpec, e);
        }

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try {
            connection.setRequestProperty("User-Agent", USER_AGENT);
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            int responseCode = connection.getResponseCode();

            int retryAfter = -1;
            String retryAfterHeader = connection.getHeaderField("Retry-After");

            if (retryAfterHeader != null) {
                try {
                    retryAfter = Integer.parseInt(retryAfterHeader);
                } catch (NumberFormatException ignored) {}
            }

            InputStream inputStream = responseCode >= 400
                    ? connection.getErrorStream()
                    : connection.getInputStream();

            StringBuilder sb = new StringBuilder();

            if (inputStream != null) {
                try (BufferedReader reader = new BufferedReader(
                        new InputStreamReader(
                                inputStream,
                                StandardCharsets.UTF_8))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line).append('\n');
                    }
                }
            }
            return new HttpResponse(responseCode, retryAfter, sb.toString());
        } finally {
            connection.disconnect();
        }
    }

    /**
    * Returns the remaining rate-limit delay for the specified host and port.
    *
    * <p>The value is rounded up to ensure that the next request is not attempted
    * before the block has expired.</p>
    *
    * @param host host and port used as the rate-limit key
    * @return remaining delay in seconds, or {@code 0} if no active block exists
    */
    private static synchronized int getRetryAfter(String host) {
        Long until = blockedUntil.get(host);
        if (until == null)
            return 0;

        return (int) ((until - System.currentTimeMillis() + 999L) / 1000L);
    }

    /**
    * Returns whether new requests to the specified URL are currently blocked
    * by the shared HTTP rate limit.
    *
    * <p>If the block has expired, its entry is removed from the rate-limit
    * table.</p>
    *
    * @param url URL whose host and port should be checked
    * @return {@code true} while the rate-limit block is active
    * @throws IOException if the URL is invalid
    */
    public static boolean isBlocked(String url) throws IOException {
        String host = getHost(url);

        synchronized (HttpRequest.class) {
            Long until = blockedUntil.get(host);

            if (until == null)
                return false;

            if (System.currentTimeMillis() >= until) {
                blockedUntil.remove(host);
                return false;
            }

            return true;
        }
    }

    /**
    * Checks whether requests to the specified host and port are currently
    * blocked.
    *
    * <p>If the block has expired, its entry is removed from the rate-limit
    * table.</p>
    *
    * @param host host and port used as the rate-limit key
    * @return {@code true} while the rate-limit block is active
    */
    private static boolean isBlockedHost(String host) {
        Long until = blockedUntil.get(host);

        if (until == null)
            return false;

        if (System.currentTimeMillis() >= until) {
            blockedUntil.remove(host);
            return false;
        }

        return true;
    }
    
    /**
    * Extracts the rate-limit key from a URL.
    *
    * <p>The key consists of the host name and effective port. When the URL does
    * not explicitly specify a port, the protocol's default port is used.</p>
    *
    * @param urlSpec URL from which to extract the key
    * @return host and port in the form {@code host:port}
    * @throws IOException if the URL is invalid
    */
    private static String getHost(String urlSpec) throws IOException {
        try {
            URL url = new URL(urlSpec);
            int port = url.getPort();

            if (port == -1)
                port = url.getDefaultPort();

            return url.getHost() + ":" + port;

        } catch (MalformedURLException e) {
            throw new IOException("Invalid URL format: " + urlSpec, e);
        }
    }

    /**
     * Clears the shared rate-limit state.
     *
     * <p>Package-private because this method is intended for tests.</p>
     */
    static synchronized void reset() {
        blockedUntil.clear();
    }
}
