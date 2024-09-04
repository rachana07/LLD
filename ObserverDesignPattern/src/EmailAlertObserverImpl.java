import java.util.Observer;

public class EmailAlertObserverImpl implements NotificationAlertObserver{

    String emailId;
    StockObservable observer;
    public EmailAlertObserverImpl(String emailId, StockObservable observer) {
        this.emailId = emailId;
        this.observer = observer;
    }
    @Override
    public void update() {
        sendMail(emailId+ " -- not in stock"+observer.getStockCount());
    }

    private void sendMail(String s) {
        System.out.println(s);
    }
}
