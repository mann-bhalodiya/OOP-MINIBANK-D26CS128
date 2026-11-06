
package practical_3;

import java.util.Objects;

public class Fraction {

    // Private variables to store numerator and denominator
    private int num;
    private int den;

    // Constructor to initialize and simplify the fraction
    public Fraction(int num, int den) {

        // A fraction cannot have zero as its denominator
        if (den == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero");
        }

        // Find the greatest common divisor of numerator and denominator
        int g = gcd(num, den);

        // Divide both values by GCD to reduce the fraction to lowest terms
        this.num = num / g;
        this.den = den / g;

        // Keep the denominator positive for a consistent representation
        if (this.den < 0) {
            this.num = -this.num;
            this.den = -this.den;
        }
    }

    // Method to calculate the greatest common divisor using Euclid's algorithm
    private int gcd(int a, int b) {

        // Convert negative values into positive values
        a = Math.abs(a);
        b = Math.abs(b);

        // Repeatedly calculate the remainder until it becomes zero
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }

        return a;
    }

    // Return the fraction in numerator/denominator format
    @Override
    public String toString() {
        return num + "/" + den;
    }

    // Check whether two fractions have the same simplified values
    @Override
    public boolean equals(Object obj) {

        // Both references point to the same object
        if (this == obj) {
            return true;
        }

        // Check for null or objects belonging to another class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Convert the Object reference into a Fraction reference
        Fraction f = (Fraction) obj;

        // Compare the reduced numerator and denominator
        return this.num == f.num && this.den == f.den;
    }

    // Generate a hash code using the reduced numerator and denominator
    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }
}
