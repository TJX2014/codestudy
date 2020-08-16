package condition;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import static java.util.concurrent.ThreadLocalRandom.current;

public class ConditionExampleTest {

    private static int shareData = 0;
    private static boolean dataUsed = false;

    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    private static void change() {
        lock.lock();
        try {
            while (!dataUsed) {
                condition.await();
            }
            TimeUnit.SECONDS.sleep(current().nextInt(5));
            shareData++;
            dataUsed = false;
            System.out.println("produce new value:" + shareData);
//            notice:使用condition上的signal，
//            不用对象监视器的notify, notify
            condition.signal();
//            condition.notify();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void useData() {
        lock.lock();
        try {
            while (dataUsed) {
                condition.await();
            }
            TimeUnit.SECONDS.sleep(current().nextInt(5));
            dataUsed = true;
            System.out.println("the shared data has changed:" + shareData);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        new Thread(() -> {
            for (; ; ) {
                change();
            }
        }, "Producer").start();
        new Thread(() -> {
            for (; ; ) {
                useData();
            }
        }, "Consumer").start();
    }
}
