package methodhandle;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class FooHandle {

    public void print(String s) {
        System.out.println("hello:"+s);
    }

    public static void main(String... args) {
        FooHandle fooHandle = new FooHandle();

        MethodType methodType = MethodType.methodType(void.class, String.class);

        MethodHandle methodHandle = null;
        try {
            methodHandle = MethodHandles.lookup().findVirtual(FooHandle.class, "print", methodType);
//            methodHandle.invokeExact(fooHandle, "xiaomin");
            methodHandle.invokeExact(fooHandle, "xiaomin");
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }

}
