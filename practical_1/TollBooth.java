
package practical_1;

import java.util.Scanner;

// Record to store the number of vehicles and their type
record Vehicle(String number, String type) {}

public class TollBooth {

    static Scanner sc = new Scanner(System.in);

    // Enum representing the number of vehicles entered by the user
    // Each enum constant stores its corresponding numeric value
    enum Numbers {
        ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5),
        SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10);

        final int value;

        // Constructor to initialize the numeric value of each enum constant
        Numbers(int value) {
            this.value = value;
        }
    }

    public static void main(String[] args) {

        // Counters to keep track of the total number of each vehicle type
        int bikeCount = 0;
        int carCount = 0;
        int truckCount = 0;

        // Stores the total toll collected from all vehicles
        int total = 0;

        // Continuously accept vehicle entries until the user enters "done"
        while (true) {

            // Read the number of vehicles entered by the user
            String input = sc.next().toLowerCase();

            // Stop accepting input when the user enters "done"
            if (input.equals("done")) {
                break;
            }

            // Convert the input String into its corresponding enum constant
            // Example: "two" becomes Numbers.TWO
            Numbers no = Numbers.valueOf(input.toUpperCase());

            // Read the type of vehicle (bike, car, or truck)
            String type = sc.next().toLowerCase();

            // Create a Vehicle record containing the entered quantity and vehicle type
            Vehicle v = new Vehicle(no.name(), type);

            // Calculate the toll based on the vehicle type
            int toll = switch (v.type()) {

                case "bike" -> {
                    // Increase bike count by the entered quantity
                    bikeCount += no.value;

                    // Each bike pays 20, so calculate the total bike toll
                    yield 20 * no.value;
                }

                case "car" -> {
                    // Increase car count by the entered quantity
                    carCount += no.value;

                    // Each car pays 50, so calculate the total car toll
                    yield 50 * no.value;
                }

                case "truck" -> {
                    // Increase truck count by the entered quantity
                    truckCount += no.value;

                    // Each truck pays 150, so calculate the total truck toll
                    yield 150 * no.value;
                }

                default -> {
                    // Handle an invalid vehicle type without adding any toll
                    System.out.println("Invalid vehicle type!");
                    yield 0;
                }
            };

            // Add the calculated toll to the overall toll collection
            total += toll;
        }

        // Display the total toll collected after all entries are completed
        System.out.println("Total toll: " + total);

        // Assume bikes are the most frequent initially
        String frequent = "bike";
        int max = bikeCount;

        // Check whether cars have a higher count than bikes
        if (carCount > max) {
            max = carCount;
            frequent = "car";
        }

        // Check whether trucks have a higher count than the current maximum
        if (truckCount > max) {
            frequent = "truck";
        }

        // Display the vehicle type with the highest count
        System.out.println("Most frequent: " + frequent);

        // Close the Scanner after all input operations are completed
        sc.close();
    }
}
