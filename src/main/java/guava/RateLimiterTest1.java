package guava;

import com.google.common.util.concurrent.RateLimiter;

import static java.lang.Thread.currentThread;

public class RateLimiterTest1 {

    private static RateLimiter rateLimiter = RateLimiter.create(0.5);

    public static void main(String[] args) {
        for (;;) {
            testRateLimiter();
        }
    }

    private static void testRateLimiter() {
        double elapsedSecond = rateLimiter.acquire();
        System.out.println(currentThread() + " elapsed seconds:" + elapsedSecond);
    }
}
