
package practical_6;

// Interface representing any device that can be switched ON or OFF.
interface Switchable {

    // Abstract methods that implementing classes must define.
    void on();
    void off();

    // Extra methods to check the device's current state and name.
    boolean isOn();
    String getName();

    // Default method to toggle the device's state.
    // If ON, turn it OFF; otherwise, turn it ON.
    default void toggle() {

        if (isOn()) {
            off();
        } else {
            on();
        }
    }
}

// Fan class implements the Switchable interface.
class Fan implements Switchable {

    private boolean status = false;

    @Override
    public void on() {
        status = true;
        System.out.println("Fan is switched ON.");
    }

    @Override
    public void off() {
        status = false;
        System.out.println("Fan is switched OFF.");
    }

    @Override
    public boolean isOn() {
        return status;
    }

    @Override
    public String getName() {
        return "Fan";
    }
}

// Light class implements the Switchable interface.
class Light implements Switchable {

    private boolean status = false;

    @Override
    public void on() {
        status = true;
        System.out.println("Light is switched ON.");
    }

    @Override
    public void off() {
        status = false;
        System.out.println("Light is switched OFF.");
    }

    @Override
    public boolean isOn() {
        return status;
    }

    @Override
    public String getName() {
        return "Light";
    }
}

// Functional interface containing exactly one abstract method.
// It decides whether a particular device can be switched ON
// at a given hour.
@FunctionalInterface
interface SwitchPolicy {

    boolean maySwitchOn(Switchable device, int hour);
}

// Main class to demonstrate interfaces and lambda expressions.
public class RemoteControlDemo {

    public static void main(String[] args) {

        // Creating objects of Fan and Light.
        Fan fan = new Fan();
        Light light = new Light();

        // Storing different devices in one interface-type array.
        Switchable[] devices = {fan, light};

        System.out.println("===== REMOTE CONTROL =====");

        // Loop through all devices and toggle their states.
        System.out.println("\n--- Toggling Devices ---");

        for (Switchable device : devices) {
            device.toggle();
        }

        // Current hour for checking the switching policy.
        int hour = 21;

        System.out.println("\nCurrent Hour: " + hour);

        // Implementation 1: Using an anonymous class.
        // This policy allows devices to switch ON between 6 AM and 10 PM.
        SwitchPolicy dayPolicy = new SwitchPolicy() {

            @Override
            public boolean maySwitchOn(Switchable device, int hour) {

                return hour >= 6 && hour <= 22;
            }
        };

        System.out.println("\n--- Anonymous Class Policy ---");

        for (Switchable device : devices) {

            if (dayPolicy.maySwitchOn(device, hour)) {
                System.out.println(device.getName()
                        + " is allowed to switch ON.");
            } else {
                System.out.println(device.getName()
                        + " is NOT allowed to switch ON.");
            }
        }

        // Implementation 2: Using a lambda expression.
        // This policy allows the Fan to operate at any hour,
        // but the Light only between 6 AM and 10 PM.
        SwitchPolicy smartPolicy = (device, time) -> {

            if (device instanceof Fan) {
                return true;
            }

            return time >= 6 && time <= 22;
        };

        System.out.println("\n--- Lambda Expression Policy ---");

        int nightHour = 23;

        for (Switchable device : devices) {

            if (smartPolicy.maySwitchOn(device, nightHour)) {
                System.out.println(device.getName()
                        + " is allowed to switch ON.");
            } else {
                System.out.println(device.getName()
                        + " is NOT allowed to switch ON.");
            }
        }
    }
}
