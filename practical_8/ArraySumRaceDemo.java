
package practical_9;

// Demonstrates three ways to calculate an array sum using threads.

class SharedTotal {

    long total = 0;

    // Unsafe: multiple threads can overwrite each other's updates.
    public void addWithoutSync(long value) {
        total += value;
    }

    // Safe: only one thread can update total at a time.
    public synchronized void addWithSync(long value) {
        total += value;
    }

    public long getTotal() {
        return total;
    }
}

public class ArraySumRaceDemo {

    // A large array gives the threads plenty of work.
    private static final int ARRAY_SIZE = 1_000_000;

    // Use several threads, but at least two.
    private static final int THREAD_COUNT =
            Math.max(2, Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) throws InterruptedException {

        int[] numbers = new int[ARRAY_SIZE];

        // Fill the array with values from 1 to ARRAY_SIZE.
        for (int i = 0; i < ARRAY_SIZE; i++) {
            numbers[i] = i + 1;
        }

        // Calculate the correct answer using a normal loop.
        long expected = 0;

        for (int number : numbers) {
            expected += number;
        }

        System.out.println("Array size       : " + ARRAY_SIZE);
        System.out.println("Number of threads: " + THREAD_COUNT);
        System.out.println("Expected sum     : " + expected);

        // Divide the array into approximately equal sections.
        int chunkSize = (ARRAY_SIZE + THREAD_COUNT - 1) / THREAD_COUNT;

        // ------------------------------------------------
        // METHOD 1: UNSAFE SHARED TOTAL
        // ------------------------------------------------

        SharedTotal unsafeTotal = new SharedTotal();
        Thread[] unsafeThreads = new Thread[THREAD_COUNT];

        long startTime = System.nanoTime();

        for (int t = 0; t < THREAD_COUNT; t++) {

            final int start = t * chunkSize;
            final int end = Math.min(start + chunkSize, ARRAY_SIZE);

            unsafeThreads[t] = new Thread(() -> {

                // Each thread adds its array section directly
                // into the same shared total without synchronization.
                for (int i = start; i < end; i++) {
                    unsafeTotal.addWithoutSync(numbers[i]);
                }
            });
        }

        for (Thread thread : unsafeThreads) {
            thread.start();
        }

        for (Thread thread : unsafeThreads) {
            thread.join();
        }

        long unsafeTime = System.nanoTime() - startTime;

        System.out.println("\nMETHOD 1: WITHOUT SYNCHRONIZATION");
        System.out.println("Calculated sum : " + unsafeTotal.getTotal());
        System.out.println("Correct result : " + (unsafeTotal.getTotal() == expected));
        System.out.printf("Time taken     : %.3f ms%n", unsafeTime / 1_000_000.0);

        // ------------------------------------------------
        // METHOD 2: SYNCHRONIZED SHARED TOTAL
        // ------------------------------------------------

        SharedTotal safeTotal = new SharedTotal();
        Thread[] safeThreads = new Thread[THREAD_COUNT];

        startTime = System.nanoTime();

        for (int t = 0; t < THREAD_COUNT; t++) {

            final int start = t * chunkSize;
            final int end = Math.min(start + chunkSize, ARRAY_SIZE);

            safeThreads[t] = new Thread(() -> {

                // Each addition is protected by synchronized.
                for (int i = start; i < end; i++) {
                    safeTotal.addWithSync(numbers[i]);
                }
            });
        }

        for (Thread thread : safeThreads) {
            thread.start();
        }

        for (Thread thread : safeThreads) {
            thread.join();
        }

        long safeTime = System.nanoTime() - startTime;

        System.out.println("\nMETHOD 2: WITH SYNCHRONIZATION");
        System.out.println("Calculated sum : " + safeTotal.getTotal());
        System.out.println("Correct result : " + (safeTotal.getTotal() == expected));
        System.out.printf("Time taken     : %.3f ms%n", safeTime / 1_000_000.0);

        // ------------------------------------------------
        // METHOD 3: THREAD-LOCAL PARTIAL TOTALS
        // ------------------------------------------------

        // Every thread gets its own array slot for its partial sum.
        // No two threads write to the same slot.
        long[] partialTotals = new long[THREAD_COUNT];

        Thread[] localThreads = new Thread[THREAD_COUNT];

        startTime = System.nanoTime();

        for (int t = 0; t < THREAD_COUNT; t++) {

            final int threadIndex = t;
            final int start = t * chunkSize;
            final int end = Math.min(start + chunkSize, ARRAY_SIZE);

            localThreads[t] = new Thread(() -> {

                // Calculate this thread's sum locally.
                long localSum = 0;

                for (int i = start; i < end; i++) {
                    localSum += numbers[i];
                }

                // Store the result in this thread's own slot.
                partialTotals[threadIndex] = localSum;
            });
        }

        for (Thread thread : localThreads) {
            thread.start();
        }

        for (Thread thread : localThreads) {
            thread.join();
        }

        // Combine partial totals after all threads have finished.
        // The main thread is the only thread doing this addition.
        long finalTotal = 0;

        for (long partial : partialTotals) {
            finalTotal += partial;
        }

        long localTime = System.nanoTime() - startTime;

        System.out.println("\nMETHOD 3: THREAD-LOCAL PARTIAL TOTALS");
        System.out.println("Calculated sum : " + finalTotal);
        System.out.println("Correct result : " + (finalTotal == expected));
        System.out.printf("Time taken     : %.3f ms%n", localTime / 1_000_000.0);
    }
}
