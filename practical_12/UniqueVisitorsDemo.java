
package practical_12;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

// Demonstrates unique visitor counting using HashSet and LinkedHashSet.

public class UniqueVisitorsDemo {

    public static void main(String[] args) {

        // Login history containing repeated usernames.
        List<String> loginNames = Arrays.asList(
                "Amit",
                "Neha",
                "Rahul",
                "Amit",
                "Priya",
                "Neha",
                "Karan",
                "Rahul",
                "Sneha"
        );

        // ---------------- USING HASHSET ----------------

        // HashSet automatically removes duplicate usernames.
        Set<String> uniqueUsers = new HashSet<>(loginNames);

        // Display the total number of unique visitors.
        System.out.println("UNIQUE VISITOR COUNT");
        System.out.println("====================");
        System.out.println("Total unique visitors: " + uniqueUsers.size());

        // ---------------- USING LINKEDHASHSET ----------------

        // LinkedHashSet removes duplicates but preserves first-seen order.
        Set<String> orderedUniqueUsers =
                new LinkedHashSet<>(loginNames);

        System.out.println("\nFIRST THREE DISTINCT VISITORS");
        System.out.println("=============================");

        int count = 0;

        // Print only the first three distinct usernames.
        for (String user : orderedUniqueUsers) {

            if (count == 3) {
                break;
            }

            System.out.println((count + 1) + ". " + user);

            count++;
        }

        // Display all unique visitors in their original first-seen order.
        System.out.println("\nALL UNIQUE VISITORS");
        System.out.println("===================");

        for (String user : orderedUniqueUsers) {
            System.out.println(user);
        }
    }
}
