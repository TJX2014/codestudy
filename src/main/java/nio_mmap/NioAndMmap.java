package nio_mmap;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class NioAndMmap {

//    strace -ff -o out java -cp . nio_mmap.NioAndMmap

    private static final ByteBuffer out = Charset.forName("utf8").encode("hello world!!!");

    public static void main(String[] args) throws Exception {
        writeNio();
        writeMap();
        readNio();
        readMMap();
    }

    private static void writeNio() throws Exception {
        System.out.println("nio begin");
        FileOutputStream fos = new FileOutputStream(new File("/tmp/nio.txt"));
        FileChannel channel = fos.getChannel();
        System.out.println("begin write nio:");
        channel.write(out);

        // 已经写过了，需要out.position(0);重置一下，给后面写mmap做准备
        out.position(0);
        System.out.println("after write nio:");
        channel.close();
    }

    private static void writeMap() throws Exception {
        System.out.println("mmap begin");
        RandomAccessFile fos = new RandomAccessFile(new File("/tmp/mmap.txt"), "rw");
        System.out.println("get channel:");
        FileChannel channel = fos.getChannel();
        System.out.println("get mmap:");
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, out.limit());
        ByteBuffer byteBuffer = mappedByteBuffer.slice();
        System.out.println("put mmap:");
        byteBuffer.put(out);
    }

    private static void readNio() throws Exception {
        FileInputStream fis = new FileInputStream(new File("/tmp/nio.txt"));
        FileChannel channel = fis.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(fis.available());
        channel.read(byteBuffer);
        String res = new String(byteBuffer.array());
        System.out.println("after read nio:" + res);
    }

    private static void readMMap() throws Exception {
        FileInputStream fis = new FileInputStream(new File("/tmp/mmap.txt"));
        FileChannel channel = fis.getChannel();
        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_ONLY, 0, fis.available());
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<mappedByteBuffer.limit(); i++) {
            sb.append((char)mappedByteBuffer.get());
        }
        System.out.println("after read mmap:"+ sb.toString());
    }
}
