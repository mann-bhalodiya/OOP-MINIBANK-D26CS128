
package practical_5;

// Abstract parent class representing a general shape.
abstract class Shape {

    // Every child class must provide its own area calculation.
    abstract double area();
}

// Circle class inherits from Shape.
class Circle extends Shape {

    private double radius;

    // Constructor to initialize the radius.
    Circle(double radius) {
        this.radius = radius;
    }

    // Overriding area() to calculate the area of a circle.
    @Override
    double area() {
        return Math.PI * radius * radius;
    }
}

// Rectangle class inherits from Shape.
class Rectangle extends Shape {

    private double length;
    private double width;

    // Constructor to initialize length and width.
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    // Overriding area() to calculate rectangle area.
    @Override
    double area() {
        return length * width;
    }
}

// Triangle class inherits from Shape.
class Triangle extends Shape {

    private double base;
    private double height;

    // Constructor to initialize base and height.
    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    // Overriding area() to calculate triangle area.
    @Override
    double area() {
        return 0.5 * base * height;
    }
}

// Main class to demonstrate runtime polymorphism.
public class ShapeAreaDemo {

    public static void main(String[] args) {

        // Creating an array of parent type Shape.
        // It can store objects of Circle, Rectangle and Triangle.
        Shape[] shapes = {
                new Circle(5),
                new Rectangle(10, 4),
                new Triangle(6, 8),
                new Circle(3),
                new Rectangle(7, 5)
        };

        double total = 0;
        double largest = 0;

        System.out.println("===== SHAPE AREAS =====");

        // One loop handles every shape using polymorphism.
        for (Shape s : shapes) {

            // Java automatically calls the correct overridden area().
            double currentArea = s.area();

            System.out.printf("Area: %.2f%n", currentArea);

            // Add the current area to the running total.
            total += currentArea;

            // Track the largest area found so far.
            if (currentArea > largest) {
                largest = currentArea;
            }
        }

        // Display the final results.
        System.out.println("------------------------");
        System.out.printf("Total Area: %.2f%n", total);
        System.out.printf("Largest Area: %.2f%n", largest);
    }
}
