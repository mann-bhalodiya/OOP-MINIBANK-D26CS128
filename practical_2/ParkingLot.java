package practical_2;


public class ParkingLot {

    // Private counters to track the number of parked vehicles
    private int twoWheelers;
    private int fourWheelers;

    // Fixed parking capacity for each type of vehicle
    private final int twoCap;
    private final int fourCap;

    // Shared revenue collected from all parking operations
    private static long revenue = 0;

    // Constructor to initialize the parking capacities
    public ParkingLot(int twoCap, int fourCap) {

        this.twoCap = twoCap;
        this.fourCap = fourCap;

        // Initially, no vehicles are parked
        this.twoWheelers = 0;
        this.fourWheelers = 0;
    }

    // Park a vehicle based on its type
    public void park(String type) {

        // Convert input to lowercase so that TWO and two are treated equally
        type = type.toLowerCase();

        switch (type) {

            case "two" -> {

                // Check whether space is available for a two-wheeler
                if (twoWheelers < twoCap) {

                    twoWheelers++;

                    // Collect 20 as parking fee
                    revenue += 20;

                    System.out.println("Two-wheeler parked successfully.");
                } else {
                    System.out.println("Full");
                }
            }

            case "four" -> {

                // Check whether space is available for a four-wheeler
                if (fourWheelers < fourCap) {

                    fourWheelers++;

                    // Collect 40 as parking fee
                    revenue += 40;

                    System.out.println("Four-wheeler parked successfully.");
                } else {
                    System.out.println("Full");
                }
            }

            default -> System.out.println("Invalid vehicle type!");
        }
    }

    // Remove a vehicle from the parking lot
    public void leave(String type) {

        // Convert input to lowercase for case-insensitive comparison
        type = type.toLowerCase();

        switch (type) {

            case "two" -> {

                // Decrease the count only if at least one two-wheeler is parked
                if (twoWheelers > 0) {
                    twoWheelers--;
                    System.out.println("Two-wheeler left.");
                } else {
                    System.out.println("No two-wheelers available.");
                }
            }

            case "four" -> {

                // Decrease the count only if at least one four-wheeler is parked
                if (fourWheelers > 0) {
                    fourWheelers--;
                    System.out.println("Four-wheeler left.");
                } else {
                    System.out.println("No four-wheelers available.");
                }
            }

            default -> System.out.println("Invalid vehicle type!");
        }
    }

    public static void main(String[] args) {

        // Create a parking lot with space for 2 two-wheelers and 1 four-wheeler
        ParkingLot parking = new ParkingLot(2, 1);

        // Park two two-wheelers
        System.out.println("Parking vehicles:");

        parking.park("two");
        parking.park("two");

        // Try parking another two-wheeler when the section is full
        parking.park("two");

        // Park one four-wheeler
        parking.park("four");

        // Try parking another four-wheeler when the section is full
        parking.park("four");

        // Display the result after vehicles leave
        System.out.println("\nVehicles leaving:");

        parking.leave("two");
        parking.leave("four");

        // Display the final parking occupancy
        System.out.println("\nFinal Occupancy:");
        System.out.println("Two-wheelers: " + parking.twoWheelers);
        System.out.println("Four-wheelers: " + parking.fourWheelers);

        // Display the total revenue collected
        System.out.println("Total Revenue: " + revenue);
    }
}
