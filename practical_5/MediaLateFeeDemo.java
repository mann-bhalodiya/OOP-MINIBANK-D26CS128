
package practical_5;

// Abstract parent class representing library media.
abstract class Media {

    protected String title;
    protected int lateDays;

    // Constructor initializes the media title and late days.
    Media(String title, int lateDays) {
        this.title = title;
        this.lateDays = lateDays;
    }

    // Each media type must define its own late-fee calculation.
    abstract double calculateLateFee();
}

// Book class has a late fee of Rs. 5 per day.
class Book extends Media {

    Book(String title, int lateDays) {
        super(title, lateDays);
    }

    // Calculate the book's late fee.
    @Override
    double calculateLateFee() {
        return lateDays * 5;
    }
}

// DVD class has a late fee of Rs. 10 per day.
class DVD extends Media {

    DVD(String title, int lateDays) {
        super(title, lateDays);
    }

    // Calculate the DVD's late fee.
    @Override
    double calculateLateFee() {
        return lateDays * 10;
    }
}

// Magazine class has a late fee of Rs. 3 per day.
class Magazine extends Media {

    Magazine(String title, int lateDays) {
        super(title, lateDays);
    }

    // Calculate the magazine's late fee.
    @Override
    double calculateLateFee() {
        return lateDays * 3;
    }
}

// Main class to demonstrate media late-fee calculation.
public class MediaLateFeeDemo {

    public static void main(String[] args) {

        // Creating a mixed array containing different media types.
        Media[] mediaItems = {
                new Book("Java Programming", 4),
                new DVD("Java Tutorial DVD", 3),
                new Magazine("Science Monthly", 5),
                new Book("Data Structures", 2),
                new DVD("Computer Basics", 6)
        };

        double totalFees = 0;

        System.out.println("===== MEDIA LATE FEE REPORT =====");

        // One loop processes every media object polymorphically.
        for (Media m : mediaItems) {

            // Calls the correct overridden method for each media type.
            double fee = m.calculateLateFee();

            System.out.println("\nMedia Title: " + m.title);
            System.out.println("Late Days: " + m.lateDays);
            System.out.printf("Late Fee: Rs. %.2f%n", fee);

            // Add the individual fee to the total.
            totalFees += fee;
        }

        // Display the total late fees for all returned media.
        System.out.println("\n--------------------------------");
        System.out.printf("Total Late Fees: Rs. %.2f%n", totalFees);
    }
}
