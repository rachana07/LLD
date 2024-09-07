import java.util.Scanner;

public class ChainOfResponsibilityDesignATMVending {
    public static void main(String[] args) {
        Dispenser dispenser = new HundredDispenser(new FiftyDispenser(new TwentyDispenser(null)));
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the amount you want to buy: ");
        int amount = scanner.nextInt();
        dispenser.dispense(amount);
    }
}

abstract class Dispenser{
    public Dispenser nextDispenser;

    public Dispenser(Dispenser dispenser){
        this.nextDispenser = dispenser;
    }

    public void dispense(int currency){
        if(nextDispenser != null){
            nextDispenser.dispense(currency);
        }
    }
}

class HundredDispenser extends Dispenser{
    public HundredDispenser(Dispenser dispenser){
        super(dispenser);
    }

    public void dispense(int currency){
        if(currency>100){
            int num = currency/100;
            int rem = currency%100;
            System.out.println("Dispensing "+num+" "+"$100 note");
            if(rem!=0){
                super.dispense(rem);
            }
        }else{
            super.dispense(currency);
        }
    }
}

class FiftyDispenser extends Dispenser{
    public FiftyDispenser(Dispenser dispenser){
        super(dispenser);
    }

    public void dispense(int currency){
        if(currency>50){
            int num = currency/50;
            int rem = currency%50;
            System.out.println("Dispensing "+num+" "+"$50 note");
            if(rem!=0){
                super.dispense(rem);
            }
        }else{
            super.dispense(currency);
        }
    }
}

class TwentyDispenser extends Dispenser{
    public TwentyDispenser(Dispenser dispenser){
        super(dispenser);
    }

    public void dispense(int currency){
        if(currency>20){
            int num = currency/20;
            int rem = currency%20;
            System.out.println("Dispensing "+num+" "+"$20 note");
            if(rem!=0){
                super.dispense(rem);
            }
        }else{
            super.dispense(currency);
        }
    }
}