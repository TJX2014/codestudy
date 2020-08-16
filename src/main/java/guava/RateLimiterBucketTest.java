package guava;

import com.google.common.util.concurrent.Monitor;
import com.google.common.util.concurrent.RateLimiter;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import static java.lang.Thread.currentThread;

public class RateLimiterBucketTest {

    static class Request {
        private final int data;

        public Request(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        @Override
        public String toString() {
            return "Request{" +
                "data=" + data +
                '}';
        }
    }

    private final ConcurrentLinkedDeque<Request> bucket
        = new ConcurrentLinkedDeque<>();

    private final static int BUCKET_CAPACITY = 100;

    private final RateLimiter rateLimiter = RateLimiter.create(10.0D);

    private final Monitor requestMonitor = new Monitor();

    private final Monitor handleMonitor = new Monitor();

    public void submitRequest(int data) {
        this.submitRequest(new Request(data));
    }

    public void submitRequest(Request request) {
        // 桶未满
        if (requestMonitor.enterIf(requestMonitor.newGuard(
            () -> bucket.size() < BUCKET_CAPACITY))) {
            try {
                boolean result = bucket.offer(request);
                if (result) {
                    System.out.println(currentThread() + ",submit request:"
                        + request.getData() + "successfully");
                } else {
                // todo:
                    System.out.println("put into mq and retry later, request:"+request);
                }
            } finally {
                requestMonitor.leave();
            }
        } else {
            // 桶溢出，降权处理
            System.out.println("The request:"+request.getData() +
                " will be down-dimensional handle due to bucket is overflow\n" +
                " put into mq and retry later");
        }
    }

    public void handleRequest(Consumer<Request> consumer) {
        if (handleMonitor.enterIf(handleMonitor.newGuard(()->!bucket.isEmpty()))) {
            try {
                rateLimiter.acquire();
                consumer.accept(bucket.poll());
            } finally {
                handleMonitor.leave();
            }
        }
    }

    private static final AtomicInteger data = new AtomicInteger(0);
    private static final RateLimiterBucketTest bucketUsed = new RateLimiterBucketTest();

    public static void main(String[] args) {
        for (int i=0; i<10; i++) {
            new Thread(() -> {
                while (true) {
                    bucketUsed.submitRequest(data.getAndIncrement());
                    try {
                        TimeUnit.MILLISECONDS.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i=0; i<10; i++) {
            new Thread(() -> {
                while(true) {
                    bucketUsed.handleRequest(System.out::println);
                }
            }).start();
        }
    }

}
