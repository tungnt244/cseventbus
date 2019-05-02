import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.lang.reflect.Method;
import java.util.*;

class SubscriberRegistry {

    private Map<Class<?>, Set<Subscriber>> subscribers = new HashMap<>();

    void register(Object listener) {
        Set<Method> eventHandlerSet = getAnnotatedMethod(listener.getClass());
        for (Method temp: eventHandlerSet) {
            Class[] paramsType = temp.getParameterTypes();
            if(subscribers.containsKey(paramsType[0])) {
                Set<Subscriber> subscribersOfEvent = subscribers.get(paramsType[0]);
                subscribersOfEvent.add(new Subscriber(listener, temp));
            } else {
                Set<Subscriber> newSubscribers = new HashSet<>();
                Set<Subscriber> subscribersOfEvent = MoreObjects.firstNonNull(subscribers.putIfAbsent(paramsType[0], newSubscribers), newSubscribers);
                subscribersOfEvent.add(new Subscriber(listener, temp));
            }
        }

    }

    void unregister(Object listener) {
        Set<Method> eventHandlerSet = getAnnotatedMethod(listener.getClass());
        for (Method temp: eventHandlerSet) {
            Class[] paramsType = temp.getParameterTypes();
            Set<Subscriber> subscribersOfEvent = subscribers.get(paramsType[0]);
            subscribersOfEvent.remove(new Subscriber(listener, temp));
        }
    }

    Set<Subscriber> getSubscribers(Object event) {
        return Optional.ofNullable(subscribers.get(event.getClass())).orElse(new HashSet<>());
    }

    private Set<Method> getAnnotatedMethod(Class target) {
        Method[] listMethod = target.getDeclaredMethods();
        Set<Method> results = new HashSet<>();
        for (Method temp: listMethod) {
            if(temp.isAnnotationPresent(Subscribe.class)) {
                Class[] paramsType = temp.getParameterTypes();
                Preconditions.checkArgument(paramsType.length > 0, "Event handler must have at least 1 argument\n"
                        + "This one have "
                +paramsType.length + " arguments");
                results.add(temp);
            }
        }
        return results;
    }
}
