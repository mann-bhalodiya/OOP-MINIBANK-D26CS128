package practical_3;

import java.util.Objects;

public class Point {

    // Private coordinates of the point
    private int x;
    private int y;

    // Constructor to initialize the coordinates
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Return the point in the format (x, y)
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // Check whether two Point objects have the same coordinates
    @Override
    public boolean equals(Object obj) {

        // If both references point to the same object, they are equal
        if (this == obj) {
            return true;
        }

        // Check whether the given object is null or belongs to another class
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        // Convert the Object reference into a Point reference
        Point p = (Point) obj;

        // Two points are equal only when both coordinates match
        return this.x == p.x && this.y == p.y;
    }

    // Generate a hash code using both coordinates
    // Equal points will always have the same hash code
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
