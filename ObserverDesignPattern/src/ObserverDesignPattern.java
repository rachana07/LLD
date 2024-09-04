import java.util.Observer;

public class ObserverDesignPattern {
    public static void main(String[] args) {
        StockObservable observable = new IphoneObservable();
        NotificationAlertObserver observer1 = new EmailAlertObserverImpl("racha@gmail.com", observable);
        NotificationAlertObserver observer2 = new MobileAlertImpl(233242352, observable);
        observable.add(observer1);
        observable.add(observer2);
        observable.notifySubscribers();
    }
}

// An observer design pattern is used when you are interested in the state of the object and want to get notified whenever there is any change. If observer changes, observable gets notified.
//Observer design pattern is behavioral design pattern(communication between objects on how they interact and distribute work)

// use case: whenever the stock is empty in amazon notify the subscribers.