package com.worddict.worddictcore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class HttpRequest {
    private static final String USER_AGENT = "WordDict/1.0 (OpenJDK)";
    private static final long EXTRA_DELAY_MS = 1000L;
    private static long blockedUntil;

    interface RequestExecutor {
        HttpResponse get(String url) throws IOException;
    }

    public static HttpResponse get(String url) throws IOException {
        return get(url, HttpRequest::getUrl);
    }

    public static HttpResponse get(String url, RequestExecutor executor)
            throws IOException {
        synchronized (HttpRequest.class) {
            if (isBlocked()) {
                return new HttpResponse(
                        HttpResponse.HTTP_TOO_MANY_REQUESTS,
                        getRetryAfter(),
                        "");
            }
        }
        HttpResponse response = executor.get(url);
        if (response.isTooManyRequests()) {
            synchronized (HttpRequest.class) {
                blockedUntil = Math.max(
                        blockedUntil,
                        System.currentTimeMillis()
                                + response.getRetryAfter() * 1000L
                                + EXTRA_DELAY_MS);
            }
        }
        return response;
    }

    /**
     * Fetches a URL and returns the HTTP response.
     *
     * @param urlSpec the URL to fetch (must not be null or empty)
     * @return the {@link HttpResponse}, including status code, Retry-After
     * and response body.
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

    private static int getRetryAfter() {
        return (int) ((blockedUntil - System.currentTimeMillis() + 999L) / 1000L);
    }

    public static synchronized boolean isBlocked() {
        return System.currentTimeMillis() < blockedUntil;
    }

    static synchronized void reset() {
        blockedUntil = 0;
    }
}