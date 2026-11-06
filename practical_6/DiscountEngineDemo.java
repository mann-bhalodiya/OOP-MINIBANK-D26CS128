
package practical_6;

import java.util.Scanner;

// Functional interface for applying a discount rule.
@FunctionalInterface
interface DiscountRule {

    // Returns the discount amount for the given original price.
    double calculateDiscount(double price);
}

// Main class to demonstrate the discount engine.
public class DiscountEngineDemo {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("===== DISCOUNT ENGINE =====");

        // Ask the user to enter the original product price.
        System.out.print("Enter original price: Rs. ");
        double price = sc.nextDouble();

        // Check whether the entered price is valid.
        if (price < 0) {
            System.out.println("Invalid price! Price cannot be negative.");
            sc.close();
            return;
        }

        // Display the available discount rules.
        System.out.println("\nSelect a Discount Rule:");
        System.out.println("1. Student Discount (10%)");
        System.out.println("2. Festival Discount (20%)");
        System.out.println("3. Membership Discount (15%)");
        System.out.println("4. No Discount (0%)");

        // Ask the user to select a discount.
        System.out.print("\nEnter your choice: ");
        int choice = sc.nextInt();

        // Reference of the functional interface.
        DiscountRule selectedRule;

        // Select the appropriate lambda expression at runtime.
        switch (choice) {

            case 1:
                // Student discount: 10% of the original price.
                selectedRule = (amount) -> amount * 0.10;
                System.out.println("\nStudent discount selected.");
                break;

            case 2:
                // Festival discount: 20% of the original price.
                selectedRule = (amount) -> amount * 0.20;
                System.out.println("\nFestival discount selected.");
                break;

            case 3:
                // Membership discount: 15% of the original price.
                selectedRule = (amount) -> amount * 0.15;
                System.out.println("\nMembership discount selected.");
                break;

            case 4:
                // No discount is applied.
                selectedRule = (amount) -> 0;
                System.out.println("\nNo discount selected.");
                break;

            default:
                // Handle invalid menu choices.
                System.out.println("Invalid choice! Please select 1 to 4.");
                sc.close();
                return;
        }

        // Apply the selected lambda rule to calculate the discount.
        double discount = selectedRule.calculateDiscount(price);

        // Calculate the final price after subtracting the discount.
        double finalPrice = price - discount;

        // Display the complete bill.
        System.out.println("\n===== DISCOUNT BILL =====");

        System.out.printf("Original Price : Rs. %.2f%n", price);
        System.out.printf("Discount Amount: Rs. %.2f%n", discount);
        System.out.printf("Final Price    : Rs. %.2f%n", finalPrice);

        System.out.println("-------------------------");
        System.out.println("Thank you for shopping!");

        sc.close();
    }
}
