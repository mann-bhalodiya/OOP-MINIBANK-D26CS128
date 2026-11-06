package practical_3;

public class PointDriver {

    public static void main(String[] args) {

        // Create an array containing some repeated points
        Point[] points = {
                new Point(2, 3),
                new Point(4, 5),
                new Point(2, 3),
                new Point(6, 7),
                new Point(4, 5)
        };

        // Stores the number of unique points
        int distinctCount = 0;

        // Check each point against all the points that appeared before it
        for (int i = 0; i < points.length; i++) {

            boolean alreadyExists = false;

            // Compare the current point with previously visited points
            for (int j = 0; j < i; j++) {

                if (points[i].equals(points[j])) {
                    alreadyExists = true;
                    break;
                }
            }

            // Count the point only if it has not appeared earlier
            if (!alreadyExists) {
                distinctCount++;
            }
        }

        // Display the total number of distinct points
        System.out.println("Distinct: " + distinctCount);
    }
}
