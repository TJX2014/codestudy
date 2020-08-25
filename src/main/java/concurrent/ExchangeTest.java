package concurrent;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.currentThread;
import static java.util.concurrent.ThreadLocalRandom.current;

public class ExchangeTest {

    public static void main(String[] args) {
        final Exchanger<String> exchanger = new Exchanger<>();
        for (int i=1; i<3; i++) {
            final int used = i;
            new Thread(() -> {
                System.out.println(currentThread() + " start");
                try {
                    randomSleep();
                    String data = exchanger.exchange("I am from t"+used);
                    System.out.println(currentThread() + ", receive data:" + data);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(currentThread() + " end");
            }, "T"+used).start();
        }
    }

    private static void randomSleep() throws InterruptedException {
        TimeUnit.SECONDS.sleep(current().nextInt(10));
    }
}
