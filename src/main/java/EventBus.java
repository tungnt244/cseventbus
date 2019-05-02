public class EventBus {

    private SubscriberRegistry subscriberRegistry = new SubscriberRegistry();

    public void register(Object listener) {
        subscriberRegistry.register(listener);
    }

    public void unregister(Object listener) {
        subscriberRegistry.unregister(listener);
    }

    public void post(Object event) {
        subscriberRegistry.getSubscribers(event).forEach(subscriber -> subscriber.invoke(event));
    }
}
