
package practical_3;

public class FractionDriver {

    public static void main(String[] args) {

        // Create fractions with different values
        Fraction f1 = new Fraction(1, 2);
        Fraction f2 = new Fraction(2, 4);
        Fraction f3 = new Fraction(3, 6);
        Fraction f4 = new Fraction(4, 5);

        // Display all fractions after simplification
        System.out.println("Fraction 1: " + f1);
        System.out.println("Fraction 2: " + f2);
        System.out.println("Fraction 3: " + f3);
        System.out.println("Fraction 4: " + f4);

        // Check whether equivalent fractions are considered equal
        System.out.println("\nComparing fractions:");

        System.out.println("f1 equals f2: " + f1.equals(f2));
        System.out.println("f2 equals f3: " + f2.equals(f3));
        System.out.println("f1 equals f3: " + f1.equals(f3));

        // Check whether a fraction with a different value is equal
        System.out.println("f1 equals f4: " + f1.equals(f4));
    }
}
