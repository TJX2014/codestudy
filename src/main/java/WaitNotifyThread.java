public class WaitNotifyThread {
    static Object lock = new Object();

    static volatile int count = 1;

    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                while(count <= 100) {
                    synchronized (lock) {
                        try {
                            if (count % 3 ==1) {
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
                while(count <= 100) {
                    synchronized (lock) {
                        try {
                            if (count % 3 ==2) {
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
        Thread t3 = new Thread(new Runnable() {
            public void run() {
            // 最后一个数据由thread-2生成
                while(count <= 100) {
                    synchronized (lock) {
                        if (count % 3 == 0) {
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
        t3.setName("nn-thread3");
        t2.start();
        t1.start();
        t3.start();
    }
}
