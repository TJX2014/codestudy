import java.util.Collections;

public class Main {
    public static void main(String[] args) {

        int []arr = {5, 1, 3, 4, 2};
        System.out.println("before:" + arr);
        int len = arr.length;
        int count = 2;
        int []resutls = new int[count];
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len - i - 1; j++) {
                if (arr[j] > arr[j+1]) {
                    arr[j] ^= arr[j+1];
                    arr[j+1] ^= arr[j];
                    arr[j] ^= arr[j+1];
                }
            }
            if (i < count) {
                resutls[i] = arr[len - i - 1];
            }
        }
        System.out.println("after:" + arr + resutls);
    }
}
