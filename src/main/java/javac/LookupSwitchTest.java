package javac;

class LookupSwitchTest {
    public static void foo() {
        int a = 0;
        switch (a) {
            case 0:
                System.out.println("#000");
                break;
            case 1:
				System.out.println("#111");
				break;
			default:
				System.out.println("default111");
				break;
        }
    }
}
