
package practical_8;

import java.io.IOException;

// Custom resource class that implements AutoCloseable.
// This allows Java to close the resource automatically
// when used inside a try-with-resources statement.
class FileResource implements AutoCloseable {

    // Constructor simulates opening a file resource.
    FileResource() {
        System.out.println("Resource opened successfully.");
    }

    // Method that simulates processing the resource.
    public void process() throws IOException {

        System.out.println("Processing resource...");

        // Simulate an error while processing the resource.
        throw new IOException(
                "Original Error: Failed to process the file."
        );
    }

    // close() is automatically called when the try block ends.
    @Override
    public void close() throws IOException {

        System.out.println("Closing resource automatically...");

        // Simulate another error while closing the resource.
        throw new IOException(
                "Closing Error: Problem while closing the file."
        );
    }
}

// Main class to demonstrate try-with-resources.
public class AutoCloseableDemo {

    public static void main(String[] args) {

        System.out.println("===== AUTO CLOSEABLE DEMO =====");

        // Try-with-resources automatically manages the resource.
        // The close() method runs when the try block finishes,
        // even if an exception occurs inside it.
        try (FileResource resource = new FileResource()) {

            // Process the resource.
            resource.process();

        } catch (IOException e) {

            // The original processing exception is reported first.
            System.out.println("\nCaught Exception: " + e.getMessage());

            // If close() also throws an exception,
            // Java attaches it to the original exception.
            // These additional errors are called suppressed exceptions.
            Throwable[] suppressed = e.getSuppressed();

            for (Throwable error : suppressed) {

                System.out.println(
                        "Suppressed Exception: " + error.getMessage()
                );
            }
        }

        System.out.println("\nProgram continues normally.");
    }
}
