
package practical_10;

import java.util.concurrent.CountDownLatch;

// Demonstrates deadlock and its prevention using a consistent lock order.

public class DeadlockDemo {

    // Two shared bank account objects act as locks.
    private static final Object accountA = new Object();
    private static final Object accountB = new Object();

    // ------------------------------------------------
    // METHOD 1: CREATE A DEADLOCK
    // ------------------------------------------------

    public static void demonstrateDeadlock() throws InterruptedException {

        System.out.println("METHOD 1: DEADLOCK DEMONSTRATION");

        // Both threads must acquire their first lock before proceeding.
        // This makes the deadlock demonstration predictable.
        CountDownLatch bothFirstLocksAcquired = new CountDownLatch(2);

        Thread employee1 = new Thread(() -> {

            try {
                // Employee 1 locks Account A first.
                synchronized (accountA) {

                    System.out.println("Employee 1 locked Account A.");

                    bothFirstLocksAcquired.countDown();

                    // Wait until Employee 2 has locked Account B.
                    bothFirstLocksAcquired.await();

                    System.out.println("Employee 1 is waiting for Account B...");

                    // Employee 1 now tries to lock Account B.
                    synchronized (accountB) {
                        System.out.println("Employee 1 accessed both accounts.");
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }, "Employee-1");

        Thread employee2 = new Thread(() -> {

            try {
                // Employee 2 locks Account B first.
                synchronized (accountB) {

                    System.out.println("Employee 2 locked Account B.");

                    bothFirstLocksAcquired.countDown();

                    // Wait until Employee 1 has locked Account A.
                    bothFirstLocksAcquired.await();

                    System.out.println("Employee 2 is waiting for Account A...");

                    // Employee 2 now tries to lock Account A.
                    synchronized (accountA) {
                        System.out.println("Employee 2 accessed both accounts.");
                    }
                }

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        }, "Employee-2");

        // Daemon threads allow the JVM to exit even if they remain deadlocked.
        employee1.setDaemon(true);
        employee2.setDaemon(true);

        employee1.start();
        employee2.start();

        // Wait for a limited time to check whether the threads finish.
        employee1.join(1000);
        employee2.join(1000);

        // If both threads are still alive, they are stuck in the deadlock.
        if (employee1.isAlive() && employee2.isAlive()) {
            System.out.println("\nDEADLOCK DETECTED!");
            System.out.println("Employee 1 is waiting for Account B.");
            System.out.println("Employee 2 is waiting for Account A.");
        } else {
            System.out.println("No deadlock detected in this run.");
        }
    }

    // ------------------------------------------------
    // METHOD 2: PREVENT DEADLOCK
    // ------------------------------------------------

    public static void demonstrateDeadlockPrevention()
            throws InterruptedException {

        System.out.println("\nMETHOD 2: DEADLOCK PREVENTION");

        Thread employee1 = new Thread(() -> transferSafely("Employee 1"));

        Thread employee2 = new Thread(() -> transferSafely("Employee 2"));

        // Start both employees.
        employee1.start();
        employee2.start();

        // Wait for both employees to finish.
        employee1.join();
        employee2.join();

        System.out.println("Both employees completed successfully.");
        System.out.println("No deadlock occurred.");
    }

    // Both employees always acquire Account A first and Account B second.
    // A consistent lock order prevents circular waiting.
    private static void transferSafely(String employeeName) {

        synchronized (accountA) {

            System.out.println(employeeName + " locked Account A.");

            synchronized (accountB) {

                System.out.println(employeeName + " locked Account B.");

                // Simulate a short bank transfer operation.
                System.out.println(employeeName + " transferred money safely.");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {

        // First demonstrate the deadlock.
        demonstrateDeadlock();

        // Then demonstrate the corrected approach.
        demonstrateDeadlockPrevention();
    }
}
