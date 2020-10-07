package reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectTest {

    // 第15次以后采用at sun.reflect.GeneratedMethodAccessor1.invoke(Unknown Source)
    // 前几次用at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)

    // 参考:sun.reflect.NativeMethodAccessorImpl.invoke
    //-Dsun.reflect.inflationThreshold=2 对应:sun.reflect.NativeMethodAccessorImpl.invoke，次数大于2时生成代码
    // -Dsun.reflect.noInflation=true 对应:sun.reflect.ReflectionFactory.newMethodAccessor，一开始就生成代码

    private static int count = 0;

    public static void foo() {
        new Exception("test reflect#"+(count++)).printStackTrace();
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<?> clzTest = Class.forName("reflect.ReflectTest");
        for (int i=0; i<20; i++) {
            Method m = clzTest.getMethod("foo");
            m.invoke(null);
        }
    }
}
