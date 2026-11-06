
package practical_9;

// Demonstrates how synchronization prevents seat overselling.

class TicketCounter {

    private int seatsLeft = 5;

    // Unsafe booking method: multiple threads may read the same seatsLeft.
    public boolean bookWithoutSync() {

        if (seatsLeft > 0) {

            // Small delay makes it easier to observe the race condition.
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }

            seatsLeft--;
            return true;
        }

        return false;
    }

    // Safe booking method: only one thread can check and book at a time.
    public synchronized boolean bookWithSync() {

        if (seatsLeft > 0) {

            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return false;
            }

            seatsLeft--;
            return true;
        }

        return false;
    }

    public int getSeatsLeft() {
        return seatsLeft;
    }
}

public class SeatBookingDemo {

    private static final int TOTAL_SEATS = 5;
    private static final int THREADS = 10;

    public static void main(String[] args) throws InterruptedException {

        // ---------------- WITHOUT SYNCHRONIZATION ----------------
        TicketCounter unsafeCounter = new TicketCounter();

        // Stores how many bookings succeeded.
        int[] unsafeSuccess = {0};

        Thread[] unsafeThreads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {

            unsafeThreads[i] = new Thread(() -> {

                if (unsafeCounter.bookWithoutSync()) {

                    // Synchronize this small update so the booking count
                    // itself does not introduce another race.
                    synchronized (unsafeSuccess) {
                        unsafeSuccess[0]++;
                    }

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " booked a seat (without synchronization)"
                    );
                }
            }, "Customer-" + (i + 1));
        }

        for (Thread thread : unsafeThreads) {
            thread.start();
        }

        for (Thread thread : unsafeThreads) {
            thread.join();
        }

        System.out.println("\nWITHOUT SYNCHRONIZATION");
        System.out.println("Successful bookings : " + unsafeSuccess[0]);
        System.out.println("Seats left           : " + unsafeCounter.getSeatsLeft());

        // ---------------- WITH SYNCHRONIZATION ----------------
        TicketCounter safeCounter = new TicketCounter();

        int[] safeSuccess = {0};

        Thread[] safeThreads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {

            safeThreads[i] = new Thread(() -> {

                if (safeCounter.bookWithSync()) {

                    // Count successful bookings safely as well.
                    synchronized (safeSuccess) {
                        safeSuccess[0]++;
                    }

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " booked a seat (with synchronization)"
                    );
                }
            }, "Customer-" + (i + 1));
        }

        for (Thread thread : safeThreads) {
            thread.start();
        }

        for (Thread thread : safeThreads) {
            thread.join();
        }

        System.out.println("\nWITH SYNCHRONIZATION");
        System.out.println("Successful bookings : " + safeSuccess[0]);
        System.out.println("Seats left           : " + safeCounter.getSeatsLeft());

        // Check whether the synchronized booking worked correctly.
        System.out.println(
                "Exactly 5 bookings? : " + (safeSuccess[0] == TOTAL_SEATS)
        );
    }
}
