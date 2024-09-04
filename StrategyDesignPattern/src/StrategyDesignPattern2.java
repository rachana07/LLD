import java.util.List;

public class StrategyDesignPattern2{
   public static void main(String[] args) {
       Item item = new Item("pen","blue",25);
       Cart cart = new Cart(item);
       cart.addItem(item);
       cart.payItem(new PaypalStrategy());
   }
}

interface PaymentStrategy{
    public void pay(int amt);
}

class CreditDebitCardStrategy implements PaymentStrategy{

    @Override
    public void pay(int amt) {
        System.out.println(amt + " - Credit Card Payment");
    }
}

class PaypalStrategy implements PaymentStrategy{

    @Override
    public void pay(int amt) {
        System.out.println(amt + " - Paypal Payment");
    }
}

class Item{
    String name;
    String color;
    int price;
    Item(String name, String color, int price) {
        this.name = name;
        this.color = color;
        this.price = price;
    }
}

class Cart{
    Item item;
    Cart(Item item) {
        this.item = item;
    }

    public String addItem(Item item){
        return item.name;
    }

    public String removeItem(Item item){
        return item.name;
    }

    public void payItem(PaymentStrategy paymentStrategy){
        paymentStrategy.pay(item.price);
    }
}