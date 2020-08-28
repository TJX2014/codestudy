import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SocketTest {

    public static void main(String[] args) throws IOException {
        Socket s = new Socket();
        // 本地端口是11111
        s.bind(new InetSocketAddress("localhost", 11111));
        // nc -l 22222
        s.connect(new InetSocketAddress("localhost", 22222));
    }
}
