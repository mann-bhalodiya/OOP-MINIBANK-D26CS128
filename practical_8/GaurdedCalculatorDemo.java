
package practical_8;

import java.util.InputMismatchException;
import java.util.Scanner;

// Custom exception for division by zero.
class DivideByZeroException extends Exception {

    // Constructor passes the error message to the parent Exception class.
    DivideByZeroException(String message) {
        super(message);
    }
}

// Main class containing the guarded calculator.
public class GaurdedCalculatorDemo {

    // Method to perform the requested arithmetic operation.
    static double calculate(double num1, double num2, char operator)
            throws DivideByZeroException {

        switch (operator) {

            case '+':
                return num1 + num2;

            case '-':
                return num1 - num2;

            case '*':
                return num1 * num2;

            case '/':

                // Check for division by zero before performing division.
                if (num2 == 0) {
                    throw new DivideByZeroException(
                            "Cannot divide a number by zero!"
                    );
                }

                return num1 / num2;

            default:
                throw new IllegalArgumentException(
                        "Invalid operator! Use +, -, * or /."
                );
        }
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        // The calculator keeps running until a valid calculation succeeds.
        boolean success = false;

        int attempt = 0;

        System.out.println("===== GUARDED CALCULATOR =====");

        while (!success) {

            attempt++;

            System.out.println("\nAttempt Number: " + attempt);

            try {

                // Read the first number.
                System.out.print("Enter first number: ");
                double num1 = sc.nextDouble();

                // Read the arithmetic operator.
                System.out.print("Enter operator (+, -, *, /): ");
                char operator = sc.next().charAt(0);

                // Read the second number.
                System.out.print("Enter second number: ");
                double num2 = sc.nextDouble();

                // Perform the calculation.
                double result = calculate(num1, num2, operator);

                // Display the result if the calculation succeeds.
                System.out.println("\nResult: " + result);

                success = true;

            } catch (DivideByZeroException e) {

                // Handle the custom division-by-zero exception.
                System.out.println("Calculation Error: " + e.getMessage());

            } catch (InputMismatchException e) {

                // Handle non-numeric input separately.
                System.out.println(
                        "Input Error: Please enter valid numeric values."
                );

                // Remove the invalid token so the next attempt can continue.
                sc.nextLine();

            } catch (IllegalArgumentException e) {

                // Handle unsupported arithmetic operators.
                System.out.println("Operator Error: " + e.getMessage());

            } finally {

                // This block executes after every attempt,
                // regardless of success or failure.
                System.out.println(
                        "Log: Attempt " + attempt + " completed."
                );
            }
        }

        System.out.println("\nCalculator completed successfully!");

        sc.close();
    }
}
