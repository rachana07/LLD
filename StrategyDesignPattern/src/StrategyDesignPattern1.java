public class StrategyDesignPattern1 {

    public static void main(String[] args) {
        Vehicle vehicle = new PassengerRoadVehicle();
        vehicle.drive();

    }
}

//Strategy pattern is used when we have multiple algorithms for a specific task and clients decides the actual implementation to be used at runtime
// It is a behavioral pattern (provides better interaction between objects provide loose coupling)


//Strategy pattern is used when subclasses implements same code that is not present in the baseclass
//class Vehicle{
//    public void drive(){
//        System.out.println("normal drive capability");
//    };
//}
//
//class OffRoadVehicle extends Vehicle{
//    public void drive() {
//        System.out.println("sports drive capability");
//    }
//}
//
//class PassengerVehicle extends Vehicle{
//}
//
//class SportsVehicle extends Vehicle{
//   public void drive() {
//       System.out.println("sports drive capability");
//   }
//}

//In above structure, SportsVehicle and OffRoadVehicle needs different method implementation not present in base class, so we need to apply strategy pattern like below

interface DriveStrategy {
    void drive();
}

class SportsDriveStrategy implements DriveStrategy{
    @Override
    public void drive(){
        System.out.println("sports drive capability");
    }
}

class NormalDriveStartegy implements DriveStrategy{
    @Override
    public void drive(){
        System.out.println("normal drive capability");
    }
}

class Vehicle{
    private DriveStrategy strategy;

    Vehicle(DriveStrategy strategy){
        this.strategy = strategy;
    }

    public void drive(){
        strategy.drive();
    }
}

class OffRoadVehicle extends Vehicle{

    OffRoadVehicle() {
        super(new SportsDriveStrategy());
    }
}

class SportsRoadVehicle extends Vehicle{
    SportsRoadVehicle() {
        super(new SportsDriveStrategy());
    }
}

class PassengerRoadVehicle extends Vehicle{
    PassengerRoadVehicle() {
        super(new NormalDriveStartegy());
    }
}