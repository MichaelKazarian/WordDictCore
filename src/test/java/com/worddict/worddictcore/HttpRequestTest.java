package com.worddict.worddictcore;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * Tests the thread-safe rate-limit handling of {@link HttpRequest}.
 *
 * <p>{@link HttpRequest} keeps the rate-limit state in a static field,
 * so all calls and all threads share the same {@code blockedUntil} value.
 * The tests therefore reset this state before every test via
 * {@link HttpRequest#reset()}.</p>
 *
 * <p>JUnit 4 creates a new instance of this test class for every
 * {@code @Test} method. Consequently, the {@code calls} counter and
 * {@code executor} below are reset for every test instance, while the
 * static state of {@link HttpRequest} is shared between tests and must
 * be reset explicitly.</p>
 *
 * <p>The executor is a test double for the real HTTP transport. On its
 * first invocation it returns HTTP 429 with a one-second Retry-After value;
 * subsequent invocations return HTTP 200. This makes the rate-limit
 * behaviour deterministic and independent of the network.</p>
 */
public class HttpRequestTest {

    private final AtomicInteger calls = new AtomicInteger();

    private final HttpRequest.RequestExecutor executor = url -> {
        if (calls.incrementAndGet() == 1) {
            return new HttpResponse(
                    HttpResponse.HTTP_TOO_MANY_REQUESTS,
                    1,
                    "");
        }
        return new HttpResponse(200, -1, "OK");
    };

    /**
     * Resets the shared HTTP rate-limit state and the per-test invocation
     * counter before each test.
     *
     * <p>The {@link HttpRequest#blockedUntil} state is static and therefore
     * survives between different test instances created by JUnit 4.
     * The {@code calls} counter is an instance field and belongs only to
     * the current test instance, but resetting it explicitly makes the
     * intended initial state clear.</p>
     */
    @Before
    public void setUp() {
        HttpRequest.reset();
        calls.set(0);
    }

    /**
     * Verifies that an actual HTTP 429 response is returned unchanged
     * and causes the request to enter the blocked state.
     *
     * <p>The test executor is invoked once and returns HTTP 429 with
     * {@code Retry-After: 1}. {@link HttpRequest} must return that response
     * and record the corresponding rate-limit period.</p>
     */
    @Test
    public void testGetReturnsTooManyRequestsResponse() throws IOException {
        HttpResponse response = HttpRequest.get("test", executor);
        assertTrue(response.isTooManyRequests());
        assertEquals(1, calls.get());
    }

    /**
     * Verifies that a request made while the shared rate-limit is active
     * is rejected without invoking the underlying HTTP transport.
     *
     * <p>The first call receives HTTP 429 and activates the shared
     * {@code blockedUntil} state. The second call is made immediately
     * afterwards and must return a synthetic HTTP 429 without calling
     * the executor again.</p>
     *
     * <p>This test is important because {@link HttpRequest#blockedUntil}
     * is static: the block applies to all callers sharing {@link HttpRequest},
     * not just to the code that received the original 429.</p>
     */
    @Test
    public void testGetRejectsRequestWhileBlocked() throws IOException {
        HttpResponse response = HttpRequest.get("test", executor);
        assertTrue(response.isTooManyRequests());

        response = HttpRequest.get("test", executor);

        assertTrue(response.isTooManyRequests());
        assertEquals(1, calls.get());
    }

    /**
     * Verifies that requests are allowed again after the rate-limit
     * period has expired.
     *
     * <p>The first call receives HTTP 429 with {@code Retry-After: 1}.
     * {@link HttpRequest} additionally applies its configured one-second
     * safety delay. The test therefore waits slightly longer than the
     * resulting two-second block.</p>
     *
     * <p>The second call must then reach the executor and receive
     * HTTP 200.</p>
     */
    @Test
    public void testGetAllowsRequestAfterBlockExpires()
            throws IOException, InterruptedException {

        HttpResponse response = HttpRequest.get("test", executor);
        assertTrue(response.isTooManyRequests());

        Thread.sleep(2100);

        response = HttpRequest.get("test", executor);

        assertTrue(response.isOk());
        assertEquals(2, calls.get());
    }
    
    @Test
    public void testGetUrl_wiktionary() throws IOException {
        String url = "https://en.wiktionary.org/wiki/test";

        HttpResponse response = HttpRequest.getUrl(url);
        assertTrue(response.isOk());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertTrue("Expected Wiktionary HTML content",
                response.getBody().contains("<title>test - Wiktionary"));
    }

    @Test(expected = IOException.class)
    public void testGetUrl_invalidUrl() throws IOException {
        HttpRequest.getUrl("invalid_url");
    }

    @Test
    public void testGetUrl_nonexistentPage() throws IOException {
        String url = "https://en.wiktionary.org/wiki/this_page_should_not_exist";
        HttpResponse response = HttpRequest.getUrl(url);
        assertFalse(response.isOk());
    }
}
