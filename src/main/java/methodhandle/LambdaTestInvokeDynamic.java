package methodhandle;

public class LambdaTestInvokeDynamic {

//    java.lang.invoke.InnerClassLambdaMetafactory
//-Djdk.internal.lambda.dumpProxyClasses=/tmp/dumpClass

    public static void main(String[] args) {
        Runnable r = () -> {
            System.out.println("hello, lambda");
        };
        r.run();
    }

}
