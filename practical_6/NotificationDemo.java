
package practical_6;

// Functional interface for sending notifications.
@FunctionalInterface
interface Notifier {

    // Single abstract method to send a message.
    void send(String message);
}

// Marker interface used to identify urgent notification senders.
// It does not contain any methods.
interface Urgent {
}

// Main class to demonstrate notification broadcasting.
public class NotificationDemo {

    public static void main(String[] args) {

        // Lambda expression for a normal email sender.
        Notifier emailSender = (message) -> {
            System.out.println("Email Notification: " + message);
        };

        // Lambda expression for an urgent SMS sender.
        // The intersection cast makes this object implement
        // both Notifier and Urgent.
        Notifier smsSender = (Notifier & Urgent) (message) -> {
            System.out.println("SMS Notification: " + message);
        };

        // Lambda expression for an urgent backup email sender.
        Notifier backupEmailSender = (Notifier & Urgent) (message) -> {
            System.out.println("Backup Email Notification: " + message);
        };

        // Store all notification senders in one array.
        Notifier[] senders = {
                emailSender,
                smsSender,
                backupEmailSender
        };

        // Message to be broadcast to all notification senders.
        String message = "Important: Practical exam is tomorrow!";

        System.out.println("===== NOTIFICATION SYSTEM =====");

        // First broadcast: Send the message once to every sender.
        System.out.println("\n--- Broadcasting Message ---");

        for (Notifier sender : senders) {

            // Calls the send() method of each lambda.
            sender.send(message);
        }

        // Second broadcast: Send the message again only
        // to notification senders implementing Urgent.
        System.out.println("\n--- Sending Again to Urgent Senders ---");

        for (Notifier sender : senders) {

            // instanceof checks whether the sender is marked Urgent.
            if (sender instanceof Urgent) {

                System.out.println("Urgent sender detected!");

                // Urgent senders receive the message a second time.
                sender.send(message);
            }
        }

        System.out.println("\nNotification broadcasting completed.");
    }
}
