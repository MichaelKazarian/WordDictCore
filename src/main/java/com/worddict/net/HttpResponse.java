package com.worddict.net;

import java.net.HttpURLConnection;


/**
 * Represents an HTTP response returned by {@link HttpRequest}.
 *
 * <p>The response contains the HTTP status code, the value of the
 * {@code Retry-After} header, if present, and the response body.</p>
 *
 * <p>{@link #isTooManyRequests()} identifies an
 * {@link #HTTP_TOO_MANY_REQUESTS} response that contains a valid positive
 * retry delay.</p>
 */
public class HttpResponse {
    public static final int HTTP_TOO_MANY_REQUESTS = 429;
    private final int statusCode;
    private final int retryAfter;
    private final String body;

    /**
     * Creates an HTTP response.
     *
     * @param statusCode HTTP status code
     * @param retryAfter retry delay in seconds, or a negative value if not provided
     * @param body response body
     */
    public HttpResponse(int statusCode, int retryAfter, String body) {
        this.statusCode = statusCode;
        this.retryAfter = retryAfter;
        this.body = body;
    }

    /**
     * Returns the HTTP status code.
     *
     * @return HTTP status code
     */
    public int getStatusCode() {
        return statusCode;
    }

     /**
     * Returns the server-specified retry delay.
     *
     * @return retry delay in seconds, or a negative value if not provided
     */
    public int getRetryAfter() {
        return retryAfter;
    }

    /**
     * Returns the response body.
     *
     * @return response body
     */
    public String getBody() {
        return body;
    }

    /**
     * Checks whether the response is an
     * {@link #HTTP_TOO_MANY_REQUESTS} response with a positive retry delay.
     *
     * @return {@code true} if the response can be handled as a rate-limit response
     */    
    public boolean isTooManyRequests() {
        return getStatusCode() == HTTP_TOO_MANY_REQUESTS && getRetryAfter() > 0;
    }

    /**
     * Checks whether the request completed successfully with HTTP 200 OK.
     *
     * @return {@code true} if the status code is HTTP 200 OK
     */
    public boolean isOk() {
        return statusCode == HttpURLConnection.HTTP_OK;
    }
}
