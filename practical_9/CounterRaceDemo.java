
package practical_9;

// Demonstrates a race condition and how synchronized fixes it.

class Counter {

    private int count = 0;

    // This method is NOT synchronized.
    // Multiple threads can update count at the same time.
    public void incrementWithoutSync() {
        count++;
    }

    // This method is synchronized.
    // Only one thread can execute it at a time on this object.
    public synchronized void incrementWithSync() {
        count++;
    }

    public int getCount() {
        return count;
    }
}

public class CounterRaceDemo {

    // Number of threads and increments per thread.
    private static final int THREADS = 10;
    private static final int INCREMENTS = 100_000;

    public static void main(String[] args) throws InterruptedException {

        // ---------------- WITHOUT SYNCHRONIZATION ----------------
        Counter unsafeCounter = new Counter();

        Thread[] unsafeThreads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            unsafeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    unsafeCounter.incrementWithoutSync();
                }
            });
        }

        // Start all threads.
        for (Thread thread : unsafeThreads) {
            thread.start();
        }

        // Wait until all threads finish.
        for (Thread thread : unsafeThreads) {
            thread.join();
        }

        int expected = THREADS * INCREMENTS;

        System.out.println("WITHOUT SYNCHRONIZATION");
        System.out.println("Expected count : " + expected);
        System.out.println("Actual count   : " + unsafeCounter.getCount());

        // ---------------- WITH SYNCHRONIZATION ----------------
        Counter safeCounter = new Counter();

        Thread[] safeThreads = new Thread[THREADS];

        for (int i = 0; i < THREADS; i++) {
            safeThreads[i] = new Thread(() -> {
                for (int j = 0; j < INCREMENTS; j++) {
                    safeCounter.incrementWithSync();
                }
            });
        }

        // Start all synchronized threads.
        for (Thread thread : safeThreads) {
            thread.start();
        }

        // Wait for all threads to finish.
        for (Thread thread : safeThreads) {
            thread.join();
        }

        System.out.println("\nWITH SYNCHRONIZATION");
        System.out.println("Expected count : " + expected);
        System.out.println("Actual count   : " + safeCounter.getCount());
    }
}
