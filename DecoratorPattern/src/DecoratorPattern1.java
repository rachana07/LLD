public class DecoratorPattern1 {
    public static void main(String[] args) {
        Pizza pizza = new Chicken();
        System.out.println("Chicken Pizza   " + pizza.price());

        Pizza extraCheese = new ExtraCheese(pizza);
        extraCheese.price();
        System.out.println("Chicken Pizza with cheese  " + extraCheese.price());

    }


}

//Structural Design Pattern provides different ways to create a class structure(create large object from small objects)
//Decorator pattern is structural design pattern
//the decorator pattern is used to modify the functionality of the object at runtime without affecting the other instances of the same class, so individual object gets modified behavior.

interface Pizza{
     int price();
}

class Veggie implements Pizza{

    @Override
    public int price() {
        return 10;
    }
}

class Chicken implements Pizza{

    @Override
    public int price() {
        return 20;
    }
}

interface DecoratorToppings extends Pizza{

    @Override
    int price();
}

class ExtraCheese implements DecoratorToppings{

    Pizza pizza;

    ExtraCheese(Pizza pizza){
        this.pizza = pizza;
    }

    @Override
    public int price() {
        return this.pizza.price()+1;
    }
}

class Mushroom implements DecoratorToppings{

    Pizza pizza;
    Mushroom(Pizza pizza){
        this.pizza = pizza;
    }

    @Override
    public int price() {
        return this.pizza.price()+2;
    }
}