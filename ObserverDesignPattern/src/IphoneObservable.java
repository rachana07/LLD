import java.util.ArrayList;
import java.util.List;

public class IphoneObservable implements StockObservable {

    List<NotificationAlertObserver> observerList = new ArrayList<NotificationAlertObserver>();
    int stockCount=0;

    @Override
    public void add(NotificationAlertObserver observer) {
        observerList.add(observer);
    }

    @Override
    public void remove(NotificationAlertObserver observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifySubscribers() {

        for(NotificationAlertObserver observer : observerList){
            observer.update();
        }

    }

    @Override
    public void setStockCount(int count) {
        if(stockCount == 0){
            notify();
        }
        stockCount= stockCount + count;
    }

    @Override
    public int getStockCount() {
        return stockCount;
    }
}
