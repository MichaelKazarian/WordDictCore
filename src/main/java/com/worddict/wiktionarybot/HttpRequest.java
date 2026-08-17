package com.worddict.wiktionarybot;

import com.worddict.worddictcore.HttpResponse;
import com.worddict.worddictcore.Utils;
import java.io.IOException;

public class HttpRequest {
    private static final long EXTRA_DELAY_MS = 1000L;

    private static long blockedUntil;

    public static HttpResponse get(String url) throws IOException {
        synchronized (HttpRequest.class) {
            if (isBlocked()) {
                return new HttpResponse(
                        HttpResponse.HTTP_TOO_MANY_REQUESTS,
                        getRetryAfter(),
                        "");
            }
        }

        HttpResponse response = Utils.getUrl(url);

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

    private static int getRetryAfter() {
        return (int) ((blockedUntil - System.currentTimeMillis() + 999L) / 1000L);
    }

    public static synchronized boolean isBlocked() {
        return System.currentTimeMillis() < blockedUntil;
    }
}