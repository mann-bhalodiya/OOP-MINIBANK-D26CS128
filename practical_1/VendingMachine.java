
package practical_1;

import java.util.Scanner;

public class VendingMachine {

    // Enum defining all the valid coin denominations accepted by the machine
    enum Coins {
        ONE, TWO, FIVE, TEN
    }

    public static void main(String[] args) {

        // Automatically closes the Scanner when the program finishes
        try (Scanner sc = new Scanner(System.in)) {

            // Fixed price of the item in cents
            final int itemPrice = 15;

            // Keeps track of the total amount inserted by the user
            int receivedAmount = 0;

            // Continue accepting coins until the received amount reaches the item price
            while (receivedAmount < itemPrice) {

                // Ask the user to enter a valid coin denomination
                System.out.println("Enter coin (ONE, TWO, FIVE, TEN):");

                // Read the user's input and convert it to uppercase
                // This allows inputs like "one" and "ONE" to be treated equally
                String input = sc.nextLine().toUpperCase();

                try {
                    // Convert the input String into its corresponding enum constant
                    // Example: "FIVE" becomes Coins.FIVE
                    Coins coin = Coins.valueOf(input);

                    // Update the received amount based on the selected coin
                    receivedAmount = switch (coin) {
                        case ONE -> receivedAmount + 1;
                        case TWO -> receivedAmount + 2;
                        case FIVE -> receivedAmount + 5;
                        case TEN -> receivedAmount + 10;
                    };

                } catch (IllegalArgumentException e) {

                    // Handle invalid input if it does not match any enum constant
                    System.out.println("Invalid coin!");
                }

                // Display the item's price and the amount received so far
                System.out.println("Item Price: " + itemPrice + " cents");
                System.out.println("Received Amount: " + receivedAmount + " cents");
            }

            // Dispense the item once the received amount reaches or exceeds its price
            System.out.println("Item dispensed!");

            // Calculate and display the remaining change, if any
            System.out.println("Change: " + (receivedAmount - itemPrice));
        }
    }
}
