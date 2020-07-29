public class WaitNotifyThread {
    static Object lock = new Object();

    static int count = 1;

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                while(count < 100) {
                    synchronized (lock) {
                        try {
                            if (count % 2 !=0) {
                                System.out.println(Thread.currentThread().getName() +":"+count++);
                            } else {
                                lock.wait();
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
            // 最后一个数据由thread-2生成
                while(count <= 100) {
                    synchronized (lock) {
                        if (count % 2 == 0) {
                            System.out.println(Thread.currentThread().getName() +":"+count++);
                        } else {
                            lock.notify();
                        }
                    }
                }
            }
        });
        t1.setName("nn-thread1");
        t2.setName("nn-thread2");
        t1.start();
        t2.start();
    }
}
