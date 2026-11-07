
package practical_11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

// Walks through a folder tree and writes a report of all regular files.

public class FolderTreeReport {

    public static void main(String[] args) {

        // Root folder that will be scanned.
        Path rootFolder = Path.of("sample_tree");

        // Report is saved outside the scanned folder.
        Path reportFile = Path.of("folder_report.txt");

        // Format used to display the last modified date and time.
        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        try {

            // Create a sample folder structure for testing.
            createSampleFolders(rootFolder);

            // Store report lines before writing them to the output file.
            List<String> report = new ArrayList<>();

            report.add("FOLDER TREE REPORT");
            report.add("==================");
            report.add("Scanned folder: " + rootFolder.toAbsolutePath());
            report.add("");

            int fileCount = 0;
            long totalSize = 0;

            // Files.walk() visits the root folder and all its subfolders.
            // try-with-resources closes the stream after processing.
            try (Stream<Path> paths = Files.walk(rootFolder)) {

                // Sort paths so the report is easier to read.
                List<Path> allPaths = paths
                        .filter(Files::isRegularFile)
                        .sorted(Comparator.naturalOrder())
                        .toList();

                // Read information about every regular file.
                for (Path file : allPaths) {

                    // Read file attributes such as size and modified time.
                    BasicFileAttributes attributes =
                            Files.readAttributes(
                                    file,
                                    BasicFileAttributes.class
                            );

                    String fileName = file.getFileName().toString();

                    // Show the path relative to the scanned root folder.
                    String relativePath = rootFolder.relativize(file).toString();

                    // Convert the last modified time into a readable format.
                    String modifiedTime = attributes.lastModifiedTime()
                            .toInstant()
                            .atZone(java.time.ZoneId.systemDefault())
                            .format(formatter);

                    long fileSize = attributes.size();

                    // Add the file information to the report.
                    report.add("File name     : " + fileName);
                    report.add("Relative path : " + relativePath);
                    report.add("File size     : " + fileSize + " bytes");
                    report.add("Modified time : " + modifiedTime);
                    report.add("----------------------------------------");

                    fileCount++;
                    totalSize += fileSize;
                }
            }

            // Add summary information at the end of the report.
            report.add("");
            report.add("SUMMARY");
            report.add("==================");
            report.add("Total files      : " + fileCount);
            report.add("Total size       : " + totalSize + " bytes");

            // Write the complete report to a text file.
            Files.write(reportFile, report);

            System.out.println("Folder scan completed successfully.");
            System.out.println("Total files found: " + fileCount);
            System.out.println("Total size: " + totalSize + " bytes");
            System.out.println(
                    "Report saved to: " + reportFile.toAbsolutePath()
            );

        } catch (IOException e) {

            // Handle errors while creating folders, scanning, or writing.
            System.out.println("Folder operation failed: " + e.getMessage());
        }
    }

    // Creates sample folders and text files for demonstration.
    private static void createSampleFolders(Path root) throws IOException {

        Path documents = root.resolve("Documents");
        Path images = root.resolve("Images");
        Path projects = documents.resolve("Projects");

        // Create the folder structure, including missing parent folders.
        Files.createDirectories(projects);
        Files.createDirectories(images);

        // Create sample files only if they do not already exist.
        createFileIfMissing(
                documents.resolve("notes.txt"),
                "These are sample notes."
        );

        createFileIfMissing(
                projects.resolve("java.txt"),
                "Java folder traversal practical."
        );

        createFileIfMissing(
                images.resolve("image-info.txt"),
                "Sample image information."
        );
    }

    // Creates a file only if it is not already present.
    private static void createFileIfMissing(Path file, String content)
            throws IOException {

        if (Files.notExists(file)) {
            Files.writeString(file, content);
        }
    }
}
