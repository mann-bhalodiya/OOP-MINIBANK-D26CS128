
package practical_8;

import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;

// Custom checked exception for insufficient stock.
class OutOfStockException extends Exception {

    // Stores the number of additional items required.
    private int shortfall;

    // Constructor initializes the shortfall and error message.
    OutOfStockException(String item, int shortfall) {
        super("Insufficient stock for " + item
                + ". Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }

    // Returns the number of additional items needed.
    public int getShortfall() {
        return shortfall;
    }
}

// Custom checked exception for invalid requested quantities.
class InvalidQuantityException extends Exception {

    InvalidQuantityException(String message) {
        super(message);
    }
}

// Warehouse class manages inventory and stock issues.
class Warehouse {

    // HashMap stores item names and their available quantities.
    private Map<String, Integer> inventory = new HashMap<>();

    // Constructor initializes the warehouse inventory.
    Warehouse() {

        inventory.put("Laptop", 10);
        inventory.put("Mouse", 20);
        inventory.put("Keyboard", 5);
    }

    // Method to issue stock for a requested item.
    // It may throw either of our custom checked exceptions.
    public void issue(String item, int qty)
            throws OutOfStockException, InvalidQuantityException {

        // Quantity must always be greater than zero.
        if (qty <= 0) {
            throw new InvalidQuantityException(
                    "Quantity must be greater than zero."
            );
        }

        // Get the currently available stock of the requested item.
        // If the item does not exist, treat its available stock as zero.
        int available = inventory.getOrDefault(item, 0);

        // Check whether the requested quantity is available.
        if (qty > available) {

            // Calculate how many more items are required.
            int shortfall = qty - available;

            throw new OutOfStockException(item, shortfall);
        }

        // Reduce inventory only after all validations succeed.
        inventory.put(item, available - qty);

        System.out.println("SUCCESS: " + qty
                + " units of " + item + " issued.");
    }

    // Displays the current inventory after processing requests.
    public void displayStock() {

        System.out.println("\n--- Remaining Warehouse Stock ---");

        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {

            System.out.println(entry.getKey()
                    + ": " + entry.getValue());
        }
    }
}

// Class representing an individual stock request.
class StockRequest {

    String item;
    int quantity;

    StockRequest(String item, int quantity) {
        this.item = item;
        this.quantity = quantity;
    }
}

// Main class to demonstrate stock issue operations.
public class StockIssueDemo {

    public static void main(String[] args) {

        // Create a warehouse object with initial inventory.
        Warehouse warehouse = new Warehouse();

        // Create a list of different stock requests.
        List<StockRequest> requests = Arrays.asList(

                new StockRequest("Laptop", 3),
                new StockRequest("Mouse", 25),
                new StockRequest("Keyboard", -2),
                new StockRequest("Keyboard", 4),
                new StockRequest("Monitor", 5),
                new StockRequest("Mouse", 5)
        );

        System.out.println("===== WAREHOUSE STOCK ISSUE =====");

        // Process each request independently.
        for (StockRequest request : requests) {

            System.out.println("\nRequest: "
                    + request.item + ", Quantity: "
                    + request.quantity);

            try {

                // Attempt to issue the requested stock.
                warehouse.issue(request.item, request.quantity);

            } catch (OutOfStockException e) {

                // Handle insufficient stock without stopping the loop.
                System.out.println("STOCK ERROR: " + e.getMessage());

                // Display the exact shortfall carried by the exception.
                System.out.println("Additional units needed: "
                        + e.getShortfall());

            } catch (InvalidQuantityException e) {

                // Handle zero or negative quantities separately.
                System.out.println("QUANTITY ERROR: " + e.getMessage());
            }
        }

        // Display inventory after all requests have been processed.
        warehouse.displayStock();

        System.out.println("\nAll stock requests processed.");
    }
}
