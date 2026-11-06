
package practical_10;

import java.util.ArrayDeque;
import java.util.Queue;

// Shared buffer used by both producer and consumer.
class SharedBuffer {

    // Queue stores the produced items.
    private final Queue<Integer> buffer = new ArrayDeque<>();

    // Maximum number of items the buffer can hold.
    private final int capacity = 3;

    // Adds an item to the buffer.
    public synchronized void produce(int item) throws InterruptedException {

        // Wait while the buffer is full.
        while (buffer.size() == capacity) {
            System.out.println("Buffer full. Producer is waiting...");
            wait();
        }

        // Add the new item to the buffer.
        buffer.add(item);

        System.out.println("Produced: " + item + " | Buffer: " + buffer);

        // Notify waiting threads that the buffer has changed.
        notifyAll();
    }

    // Removes an item from the buffer.
    public synchronized int consume() throws InterruptedException {

        // Wait while the buffer is empty.
        while (buffer.isEmpty()) {
            System.out.println("Buffer empty. Consumer is waiting...");
            wait();
        }

        // Remove the oldest item from the queue.
        int item = buffer.remove();

        System.out.println("Consumed: " + item + " | Buffer: " + buffer);

        // Notify waiting threads that space is now available.
        notifyAll();

        return item;
    }
}

// Producer thread creates items and places them in the buffer.
class Producer implements Runnable {

    private final SharedBuffer sharedBuffer;
    private final int totalItems;

    public Producer(SharedBuffer sharedBuffer, int totalItems) {
        this.sharedBuffer = sharedBuffer;
        this.totalItems = totalItems;
    }

    @Override
    public void run() {

        try {
            // Produce numbers from 1 to totalItems.
            for (int i = 1; i <= totalItems; i++) {
                sharedBuffer.produce(i);
            }

            System.out.println("Producer finished.");

        } catch (InterruptedException e) {

            // Restore interrupted status if the producer is interrupted.
            Thread.currentThread().interrupt();
            System.out.println("Producer was interrupted.");
        }
    }
}

// Consumer thread removes items from the buffer.
class Consumer implements Runnable {

    private final SharedBuffer sharedBuffer;
    private final int totalItems;

    public Consumer(SharedBuffer sharedBuffer, int totalItems) {
        this.sharedBuffer = sharedBuffer;
        this.totalItems = totalItems;
    }

    @Override
    public void run() {

        try {
            // Consume exactly totalItems items.
            for (int i = 1; i <= totalItems; i++) {
                sharedBuffer.consume();
            }

            System.out.println("Consumer finished.");

        } catch (InterruptedException e) {

            // Restore interrupted status if the consumer is interrupted.
            Thread.currentThread().interrupt();
            System.out.println("Consumer was interrupted.");
        }
    }
}

// Main class starts the producer and consumer threads.
public class ProducerConsumerDemo {

    public static void main(String[] args) throws InterruptedException {

        // Create one shared buffer with capacity 3.
        SharedBuffer sharedBuffer = new SharedBuffer();

        // Both threads will process exactly 10 items.
        int totalItems = 10;

        // Create producer and consumer threads.
        Thread producer = new Thread(
                new Producer(sharedBuffer, totalItems),
                "Producer"
        );

        Thread consumer = new Thread(
                new Consumer(sharedBuffer, totalItems),
                "Consumer"
        );

        System.out.println("Producer-Consumer problem started.\n");

        // Start both threads.
        producer.start();
        consumer.start();

        // Wait for both threads to complete.
        producer.join();
        consumer.join();

        System.out.println("\nAll 10 items produced and consumed successfully.");
    }
}
