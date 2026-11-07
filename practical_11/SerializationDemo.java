
package practical_11;

import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.io.IOException;
import java.util.Arrays;

// Student class implements Serializable so its objects can be saved.
class Student implements Serializable {

    // Used to identify the class version during deserialization.
    private static final long serialVersionUID = 1L;

    private int rollNo;
    private String name;
    private double marks;

    // transient fields are not saved during serialization.
    private transient String password;

    // Constructor to initialize student details.
    public Student(int rollNo, String name, double marks, String password) {
        this.rollNo = rollNo;
        this.name = name;
        this.marks = marks;
        this.password = password;
    }

    // Displays all student details.
    public void display() {
        System.out.println("Roll Number : " + rollNo);
        System.out.println("Name        : " + name);
        System.out.println("Marks       : " + marks);
        System.out.println("Password    : " + password);
        System.out.println("----------------------------");
    }
}

public class SerializationDemo {

    public static void main(String[] args) {

        // Name of the file in which objects will be stored.
        String fileName = "students.ser";

        // Create an array containing three Student objects.
        Student[] students = {
                new Student(101, "Amit", 85.5, "amit123"),
                new Student(102, "Neha", 92.0, "neha456"),
                new Student(103, "Rahul", 78.5, "rahul789")
        };

        // ---------------- SERIALIZATION ----------------

        try (ObjectOutputStream output =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {

            // Write the complete Student array into the file.
            output.writeObject(students);

            System.out.println("Student objects saved successfully.");

        } catch (IOException e) {
            System.out.println("Error while saving objects: " + e.getMessage());
            return;
        }

        // ---------------- DESERIALIZATION ----------------

        try (ObjectInputStream input =
                     new ObjectInputStream(new FileInputStream(fileName))) {

            // Read the saved array and convert it back into Student objects.
            Student[] loadedStudents = (Student[]) input.readObject();

            System.out.println("\nStudent objects loaded successfully.");
            System.out.println("\nDetails after loading:");

            // Display the loaded objects.
            for (Student student : loadedStudents) {
                student.display();
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error while loading objects: " + e.getMessage());
        }
    }
}
