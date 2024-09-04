public class FactoryPattern {
    public static void main(String[] args) {
        Shape shapeObject = ShapeObject.getShape("Rectangle");
        System.out.println(shapeObject.draw());

    }
}

// Factory pattern is used when we have superclass with multiple subclasses and based on input, we need to return one of the subclasses.
//it is creational design pattern(provide solutions to instantiate an object)

interface Shape {
     String draw();
}

class Rectangle implements Shape{

    @Override
    public String draw() {
        return "Drawing Rectangle";
    }
}

class Square implements Shape{

    @Override
    public String draw() {
        return "Drawing Square";
    }
}

class ShapeObject{

    public static Shape getShape(String input){
        switch (input){
            case "Rectangle":
              return new Rectangle();
            case "Square":
                return new Square();
        }

        return null;
    }
}