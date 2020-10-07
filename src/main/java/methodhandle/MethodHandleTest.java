package methodhandle;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class MethodHandleTest {

    public static void main(String[] args) {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType mt = MethodType.methodType(Object.class, Object.class, Object.class);

//        CallSite callSite = IndyInterface
    }
}
