import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket s = new Socket();
        s.bind(new InetSocketAddress("localhost", 11111));
        Thread t = new Thread(() -> {
            // 本地端口是11111
            try {
                Thread.sleep(5000);
                s.connect(new InetSocketAddress("localhost", 22222));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // nc -l 22222
        });
        t.start();
        System.out.println("begin join");
        t.join();
        System.out.println("end join");
    }
}
