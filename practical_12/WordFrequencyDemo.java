
package practical_12;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

// Demonstrates word counting using HashMap and sorting using Comparator.

public class WordFrequencyDemo {

    public static void main(String[] args) {

        // Sample paragraph containing repeated words.
        String paragraph =
                "Java is simple and Java is powerful. "
                        + "Java is popular because Java is easy to learn.";

        // Convert the complete paragraph to lowercase.
        // This makes the counting case-insensitive.
        paragraph = paragraph.toLowerCase();

        // Remove punctuation and split the paragraph into words.
        String[] words = paragraph.replaceAll("[^a-z0-9\\s]", "")
                .trim()
                .split("\\s+");

        // HashMap stores each word as a key and its frequency as a value.
        Map<String, Integer> frequencyMap = new HashMap<>();

        // Count how many times each word occurs.
        for (String word : words) {

            // If the word exists, increase its count by 1.
            // Otherwise, start its count at 1.
            frequencyMap.put(
                    word,
                    frequencyMap.getOrDefault(word, 0) + 1
            );
        }

        // Convert the map entries into a list so they can be sorted.
        List<Map.Entry<String, Integer>> sortedWords =
                new ArrayList<>(frequencyMap.entrySet());

        // Sort by frequency in descending order.
        // If frequencies are equal, sort alphabetically by word.
        sortedWords.sort(
                Comparator
                        .comparing(
                                (Map.Entry<String, Integer> entry) ->
                                        entry.getValue()
                        )
                        .reversed()
                        .thenComparing(Map.Entry::getKey)
        );

        // Display the final word frequency list.
        System.out.println("WORD FREQUENCY");
        System.out.println("==============");

        for (Map.Entry<String, Integer> entry : sortedWords) {

            System.out.println(
                    entry.getKey() + " : " + entry.getValue()
            );
        }
    }
}
