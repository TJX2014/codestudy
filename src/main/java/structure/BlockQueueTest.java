package structure;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

public class BlockQueueTest {

    public static void main(String[] args) throws InterruptedException {
        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(2);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3, 3, TimeUnit.SECONDS);
        for (Integer integer : queue) {
            System.out.println("queue element:"+ integer);
        }
    }
}
