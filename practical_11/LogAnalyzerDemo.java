
package practical_11;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Locale;

// Demonstrates reading multiple log files using Java NIO.

public class LogAnalyzerDemo {

    public static void main(String[] args) {

        // Create a folder to store our sample log files.
        Path folder = Path.of("logs");

        // Define paths for three log files.
        Path file1 = folder.resolve("application1.txt");
        Path file2 = folder.resolve("application2.txt");
        Path file3 = folder.resolve("application3.txt");

        // Keyword that we want to search for in the log files.
        String keyword = "ERROR";

        try {

            // Create the logs folder if it does not already exist.
            Files.createDirectories(folder);

            // Sample log entries for the first file.
            List<String> logs1 = List.of(
                    "Application started successfully",
                    "User logged in",
                    "ERROR: Database connection failed",
                    "User logged out"
            );

            // Sample log entries for the second file.
            List<String> logs2 = List.of(
                    "Server started",
                    "ERROR: Invalid request",
                    "Request processed successfully"
            );

            // Sample log entries for the third file.
            List<String> logs3 = List.of(
                    "Backup started",
                    "Backup completed",
                    "WARNING: Low disk space",
                    "ERROR: Backup verification failed"
            );

            // Write the sample log entries into their respective files.
            Files.write(file1, logs1);
            Files.write(file2, logs2);
            Files.write(file3, logs3);

            // Store all file paths in an array for easy processing.
            Path[] files = {file1, file2, file3};

            // Variables to store totals across all files.
            int totalLines = 0;
            int totalKeywordMatches = 0;

            System.out.println("LOG FILE ANALYSIS");
            System.out.println("=================");

            // Process each log file one by one.
            for (Path file : files) {

                System.out.println("\nFile: " + file.getFileName());

                // Read basic attributes such as file size.
                BasicFileAttributes attributes =
                        Files.readAttributes(file, BasicFileAttributes.class);

                System.out.println(
                        "File size: " + attributes.size() + " bytes"
                );

                // Read the file line by line.
                int fileLines = 0;
                int fileMatches = 0;

                try (var reader = Files.newBufferedReader(file)) {

                    String line;

                    while ((line = reader.readLine()) != null) {

                        // Count every line.
                        fileLines++;

                        // Check whether the line contains the keyword.
                        if (line.toLowerCase(Locale.ROOT)
                                .contains(keyword.toLowerCase(Locale.ROOT))) {
                            fileMatches++;
                        }
                    }
                }

                // Add this file's counts to the overall totals.
                totalLines += fileLines;
                totalKeywordMatches += fileMatches;

                System.out.println("Total lines: " + fileLines);
                System.out.println(keyword + " lines: " + fileMatches);
            }

            // Display the final totals across all log files.
            System.out.println("\nOVERALL SUMMARY");
            System.out.println("================");
            System.out.println("Total lines across all files: " + totalLines);
            System.out.println(
                    "Total " + keyword + " occurrences by line: "
                            + totalKeywordMatches
            );

        } catch (IOException e) {

            // Display an error if file creation, reading, or writing fails.
            System.out.println("File operation failed: " + e.getMessage());
        }
    }
}
