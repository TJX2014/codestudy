import java.util.Collections;

public class Main {
    public static void main(String[] args) {
        int res = sqrt(10);
        System.out.println(res);
    }

    static int sqrt(int n) {
        int i = n;
        while (n < i*i) {
            i = i/2;
        }
        int j = i+1;
        for (;j*j<n;) {
            j++;
        }
        return j-1;
    }
}
