public class MobileAlertImpl implements NotificationAlertObserver{

    int mobileNumber;
    StockObservable stockObservable;

    public MobileAlertImpl(int mobileNumber, StockObservable stockObservable) {
        this.mobileNumber = mobileNumber;
        this.stockObservable = stockObservable;
    }

    @Override
    public void update() {
        sendMessage(mobileNumber+ " -- product is not in stock" + stockObservable.getStockCount());
    }

    public void sendMessage(String message) {
        System.out.println(message);
    }
}
