public class DecoratorPattern2 {
    public static void main(String[] args) {
        Car car = new BasicCar();
        car.features();

        Car carDecorator = new BasicCarDecorator(car);
        carDecorator.features();
    }
}

interface Car{
    public void features();
}

class BasicCar implements Car{

    @Override
    public void features() {
        System.out.println("BasicCar features");
    }
}

class PremiumCar implements Car{

    @Override
    public void features() {
        System.out.println("PremiumCar features");
    }
}

class CarDecorator implements Car{
    protected Car car;
    CarDecorator(Car car){
        this.car = car;
    }

    @Override
    public void features() {
        this.car.features();
    }
}

class BasicCarDecorator extends CarDecorator{


    BasicCarDecorator(Car car) {
        super(car);
    }

    @Override
    public void features() {
        this.car.features();
        System.out.println("Basic Seating features");
    }
}

class PremiumCarDecorator extends CarDecorator{
    PremiumCarDecorator(Car car) {
        super(car);
    }

    @Override
    public void features() {
        this.car.features();
        System.out.println(" Premium Seating features");
    }
}
