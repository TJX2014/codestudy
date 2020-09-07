import java.util.HashMap;
import java.util.Map;

public class G1Test {
    private Map products = new HashMap();

    public void addItem() {
        Integer key = new Integer(4311);
        while(true) {
            String value = new String("111222");
            products.put(key, value);
        }
    }

    public static void main(String[] args) {
        G1Test test = new G1Test();
        test.addItem();
    }
}