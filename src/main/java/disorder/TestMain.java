package disorder;

public class TestMain {

    // 不乱序执行是不可能出现x==0并且y==0的

    static int x, y, a, b;

    public static void main(String[] args) throws InterruptedException {
        int i = 0;
        for (;;) {
            i++;
            x = 0; y = 0;
            a = 0; b = 0;
            Thread one = new Thread(new Runnable() {
                @Override
                public void run() {
                    a = 1;
                    x = b;
                }
            });

            Thread other = new Thread(new Runnable() {
                @Override
                public void run() {
                    b = 1;
                    y = a;
                }
            });
            one.start();other.start();
            one.join();other.join();
            String output = "第" + i + "次调用 ("+x + "," +y + ")";
            if (x == 0 && y == 0) {
                System.out.println(output);
                break;
            } else {

            }
        }
    }
}
