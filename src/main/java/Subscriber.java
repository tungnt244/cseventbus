import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

class Subscriber {

    private Object target;

    private Method method;

    Subscriber(Object target, Method method) {
        this.target = target;
        this.method = method;
    }

    void invoke(Object event) {
        try {
            this.method.invoke(target, event);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subscriber that = (Subscriber) o;
        return target == that.target &&
                method.equals(that.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(target, method);
    }
}
