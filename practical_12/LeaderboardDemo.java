
package practical_12;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

// Demonstrates creating a sorted leaderboard using Map and Comparator.

public class LeaderboardDemo {

    public static void main(String[] args) {

        // Store player names as keys and their scores as values.
        Map<String, Integer> playerScores = new HashMap<>();

        // Add player names and scores.
        playerScores.put("Amit", 850);
        playerScores.put("Neha", 950);
        playerScores.put("Rahul", 900);
        playerScores.put("Priya", 950);
        playerScores.put("Karan", 780);
        playerScores.put("Sneha", 880);
        playerScores.put("Rohan", 900);
        playerScores.put("Anjali", 820);

        // Number of top players to display.
        int topN = 5;

        // Convert the map entries into a list for sorting.
        List<Map.Entry<String, Integer>> leaderboard =
                new ArrayList<>(playerScores.entrySet());

        // Sort players by:
        // 1. Score in descending order.
        // 2. Player name in alphabetical order when scores are equal.
        leaderboard.sort(
                Comparator
                        .comparing(
                                (Map.Entry<String, Integer> entry) ->
                                        entry.getValue()
                        )
                        .reversed()
                        .thenComparing(Map.Entry::getKey)
        );

        // Display the top-N leaderboard.
        System.out.println("PLAYER LEADERBOARD");
        System.out.println("==================");

        System.out.println(
                String.format("%-8s %-15s %s", "Rank", "Player", "Score")
        );

        System.out.println("--------------------------------");

        // Display only the requested number of top players.
        int rank = 1;

        for (Map.Entry<String, Integer> entry : leaderboard) {

            // Stop after printing topN players.
            if (rank > topN) {
                break;
            }

            System.out.println(
                    String.format(
                            "%-8d %-15s %d",
                            rank,
                            entry.getKey(),
                            entry.getValue()
                    )
            );

            rank++;
        }

        System.out.println("\nTop " + topN + " players displayed.");
    }
}
