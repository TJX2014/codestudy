package concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import static java.util.concurrent.ThreadLocalRandom.current;

public class CyclicBarrierTest {

    public static void main(String[] args) throws BrokenBarrierException, InterruptedException {
        // 这里是11
        final CyclicBarrier barrier = new CyclicBarrier(11);
        // 这里是10,主线程占一个位置
        for (int i=0; i<10; i++) {
            Thread t = new Thread(new Tourist(i, barrier));
            t.setName("Tourist_"+i);
            t.start();
        }

        barrier.await();
        System.out.println("Guide:all tourist get on the bus");
        barrier.await();
        System.out.println("Guide:all tourist get off the bus");
    }

    private static class Tourist implements Runnable {

        private final int touristID;
        private final CyclicBarrier barrier;

        public Tourist(int i, CyclicBarrier barrier) {
            this.touristID = i;
            this.barrier = barrier;
        }

        @Override
        public void run() {
            System.out.printf("Tourist %d get on the bus\n", this.touristID);
            try {
                //模拟上车
                this.spendSeveralSeconds();
                //等待其他人上车
                this.waitAndPrintf("Tourist %d get on the bus, wait others\n");
                System.out.printf("Tourist %d arrive the destination\n", this.touristID);
                this.spendSeveralSeconds();
                this.waitAndPrintf("Tourist %d get off the bus, wait other\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void waitAndPrintf(String s) {
            System.out.printf(s, this.touristID);
            try {
                barrier.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (BrokenBarrierException e) {
                e.printStackTrace();
            }
        }

        private void spendSeveralSeconds() throws InterruptedException {
            TimeUnit.SECONDS.sleep(current().nextInt(10));
        }
    }
}
