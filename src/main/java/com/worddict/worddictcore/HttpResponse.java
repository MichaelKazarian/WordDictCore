package com.worddict.worddictcore;

import java.net.HttpURLConnection;

public class HttpResponse {
    private static final int HTTP_TOO_MANY_REQUESTS = 429;
    private final int statusCode;
    private final int retryAfter;
    private final String body;

    public HttpResponse(int statusCode, int retryAfter, String body) {
        this.statusCode = statusCode;
        this.retryAfter = retryAfter;
        this.body = body;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public int getRetryAfter() {
        return retryAfter;
    }

        public String getBody() {
        return body;
    }
    
    public boolean isTooManyRequests() {
        return getStatusCode() == HTTP_TOO_MANY_REQUESTS && getRetryAfter() > 0;
    }

    public boolean isOk() {
        return statusCode == HttpURLConnection.HTTP_OK;
    }
}