
package practical_10;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// Demonstrates how a fixed thread pool executes and reuses threads.

public class ThreadPoolDemo {

    public static void main(String[] args) {

        // Create a thread pool containing exactly 3 worker threads.
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Submit 10 independent tasks to the thread pool.
        for (int i = 1; i <= 10; i++) {

            // Store the task ID for use inside the Runnable.
            final int taskId = i;

            // Submit a task to the executor.
            executor.submit(() -> {

                // Display the task ID and the worker thread executing it.
                String threadName = Thread.currentThread().getName();

                System.out.println(
                        "Task " + taskId + " started by " + threadName
                );

                try {
                    // Simulate some work by sleeping for a short time.
                    Thread.sleep(1000);
                } catch (InterruptedException e) {

                    // Restore the interrupted status if the task is interrupted.
                    Thread.currentThread().interrupt();

                    System.out.println("Task " + taskId + " was interrupted.");
                    return;
                }

                System.out.println(
                        "Task " + taskId + " completed by " + threadName
                );
            });
        }

        // Stop accepting new tasks.
        executor.shutdown();

        try {
            // Wait until all submitted tasks have completed.
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {

                // Forcefully stop remaining tasks if the timeout expires.
                executor.shutdownNow();

                System.out.println("Some tasks did not finish in time.");
            } else {
                System.out.println("\nAll tasks completed successfully.");
            }

        } catch (InterruptedException e) {

            // Force shutdown if the main thread is interrupted.
            executor.shutdownNow();

            Thread.currentThread().interrupt();

            System.out.println("Main thread was interrupted.");
        }
    }
}
