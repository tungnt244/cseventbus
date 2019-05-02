
public class Demo {
    public static void main(String[] args) {
        EventBus temp = new EventBus();
        Sub1 sub1 = new Sub1();
        Sub2 sub2 = new Sub2();
        temp.register(sub1);
        temp.register(sub2);
        temp.post("hello");
        temp.post(123);
        temp.unregister(sub2);
        temp.post("hello");
    }
}
