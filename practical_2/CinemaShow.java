package practical_2;

public class CinemaShow {

    // Private variables to store show details
    private String title;
    private int seatsAvailable;

    // Capacity is fixed when the cinema show object is created
    private final int capacity;

    // Shared counter to track the total number of successfully booked seats
    private static int totalBooked = 0;

    // Constructor that accepts the movie title and total seat capacity
    public CinemaShow(String title, int capacity) {

        this.title = title;
        this.capacity = capacity;

        // Initially, all seats are available
        this.seatsAvailable = capacity;
    }

    // Constructor chaining: use 100 seats as the default capacity
    public CinemaShow(String title) {
        this(title, 100);
    }

    // Book the requested number of seats
    public boolean book(int n) {

        // Booking is allowed only if the requested seats are available
        if (n > 0 && n <= seatsAvailable) {

            // Reduce the available seats by the booked quantity
            seatsAvailable -= n;

            // Update the total number of successfully booked seats
            totalBooked += n;

            return true;
        }

        // Return false if the requested booking cannot be completed
        return false;
    }

    // Cancel the requested number of seats
    public void cancel(int n) {

        if (n > 0) {

            // Add the cancelled seats but never exceed the original capacity
            seatsAvailable = Math.min(capacity, seatsAvailable + n);
        }
    }

    // Getter method to access the number of available seats
    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    // Static getter to access the total number of successful bookings
    public static int getTotalBooked() {
        return totalBooked;
    }

    public static void main(String[] args) {

        // Create two cinema shows using the two constructors
        CinemaShow show1 = new CinemaShow("Avengers", 50);
        CinemaShow show2 = new CinemaShow("Interstellar");

        // Try booking 20 seats for the first show
        System.out.println("Booking 20 seats: " + show1.book(20));
        System.out.println("Seats available: " + show1.getSeatsAvailable());

        // Try booking another 25 seats
        System.out.println("\nBooking 25 seats: " + show1.book(25));
        System.out.println("Seats available: " + show1.getSeatsAvailable());

        // Try booking more seats than are available
        System.out.println("\nBooking 10 seats: " + show1.book(10));
        System.out.println("Seats available: " + show1.getSeatsAvailable());

        // Cancel 10 previously booked seats
        show1.cancel(10);
        System.out.println("\nAfter cancelling 10 seats:");
        System.out.println("Seats available: " + show1.getSeatsAvailable());

        // Book 5 seats for the second show
        System.out.println("\nBooking 5 seats in second show: " + show2.book(5));
        System.out.println("Seats available: " + show2.getSeatsAvailable());

        // Display the total number of seats successfully booked across both shows
        System.out.println("\nTotal Booked: " + CinemaShow.getTotalBooked());
    }
}
