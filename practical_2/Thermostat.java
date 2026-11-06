
package practical_2;

public class Thermostat {

    // Private instance variables to store thermostat details
    private String location;
    private int temperature;

    // Fixed temperature limits for all thermostats
    private static final int MIN = 16;
    private static final int MAX = 30;

    // Shared counter to keep track of created thermostat objects
    private static int activeCount = 0;

    // Constructor that accepts location and starting temperature
    public Thermostat(String location, int startTemp) {

        this.location = location;

        // Set the given temperature only if it is within the allowed range
        // Otherwise, use the default temperature of 22
        if (startTemp >= MIN && startTemp <= MAX) {
            this.temperature = startTemp;
        } else {
            this.temperature = 22;
        }

        // Increment the count whenever a new thermostat is created
        activeCount++;
    }

    // Constructor chaining: use 22 as the default starting temperature
    public Thermostat(String location) {
        this(location, 22);
    }

    // Increase the temperature by 1 if it has not reached the maximum
    public void raise() {

        if (temperature < MAX) {
            temperature++;
        } else {
            System.out.println("Already at maximum (" + MAX + ")");
        }
    }

    // Decrease the temperature by 1 if it has not reached the minimum
    public void lower() {

        if (temperature > MIN) {
            temperature--;
        } else {
            System.out.println("Already at minimum (" + MIN + ")");
        }
    }

    // Getter method to access the current temperature
    // No setter is provided so temperature can only be changed through raise() and lower()
    public int getTemperature() {
        return temperature;
    }

    // Static getter to access the total number of created thermostats
    public static int getActiveCount() {
        return activeCount;
    }

    public static void main(String[] args) {

        // Create two thermostat objects using both constructors
        Thermostat t1 = new Thermostat("Living Room", 22);
        Thermostat t2 = new Thermostat("Bedroom");

        // Increase the temperature 10 times
        System.out.println("Raising temperature:");

        for (int i = 1; i <= 10; i++) {
            t1.raise();
            System.out.println("Temperature: " + t1.getTemperature());
        }

        // Decrease the temperature 20 times
        System.out.println("\nLowering temperature:");

        for (int i = 1; i <= 20; i++) {
            t1.lower();
            System.out.println("Temperature: " + t1.getTemperature());
        }

        // Display the number of thermostat objects created
        System.out.println("\nActive Thermostats: " + Thermostat.getActiveCount());
    }
}
