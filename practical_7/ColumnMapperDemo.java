
package practical_7;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// Custom annotation used to map Java fields to column names.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Column {

    // Stores the name of the corresponding data column.
    String name();
}

// Student class whose fields will be populated using reflection.
class Student {

    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Column(name = "salary")
    private double salary;

    // Display the populated student details.
    public void display() {

        System.out.println("Student ID: " + id);
        System.out.println("Student Name: " + name);
        System.out.println("Student Age: " + age);
        System.out.println("Student Salary: Rs. " + salary);
    }
}

// Class responsible for mapping CSV-like data into objects.
class ColumnMapper {

    // Populates an object's fields using header and data arrays.
    public static List<String> populate(
            Object object,
            String[] headers,
            String[] data) throws IllegalAccessException {

        // Store warnings such as missing columns.
        List<String> warnings = new ArrayList<>();

        // Get all fields declared in the object's class.
        Field[] fields = object.getClass().getDeclaredFields();

        // Examine every field.
        for (Field field : fields) {

            // Check whether the field has the @Column annotation.
            if (field.isAnnotationPresent(Column.class)) {

                // Read the column name specified in the annotation.
                Column annotation = field.getAnnotation(Column.class);

                String columnName = annotation.name();

                // Find the matching column index in the header row.
                int columnIndex = -1;

                for (int i = 0; i < headers.length; i++) {

                    if (headers[i].trim().equalsIgnoreCase(columnName)) {
                        columnIndex = i;
                        break;
                    }
                }

                // If the column is missing, leave the field unchanged.
                if (columnIndex == -1) {

                    warnings.add("Missing column: " + columnName);
                    continue;
                }

                // Check whether the corresponding data value exists.
                if (columnIndex >= data.length) {

                    warnings.add("Missing data for column: " + columnName);
                    continue;
                }

                // Get the value from the corresponding data column.
                String value = data[columnIndex].trim();

                // Allow reflection to access private fields.
                field.setAccessible(true);

                // Convert the string value into the field's required type.
                try {

                    Class<?> type = field.getType();

                    if (type == String.class) {

                        field.set(object, value);

                    } else if (type == int.class) {

                        field.setInt(object, Integer.parseInt(value));

                    } else if (type == double.class) {

                        field.setDouble(object, Double.parseDouble(value));

                    } else {

                        warnings.add("Unsupported field type for: "
                                + columnName);
                    }

                } catch (NumberFormatException e) {

                    // Report invalid numeric values instead of stopping
                    // the entire mapping process.
                    warnings.add("Invalid value for column: " + columnName);
                }
            }
        }

        // Return all warnings found during mapping.
        return warnings;
    }
}

// Main class to demonstrate annotation-based column mapping.
public class ColumnMapperDemo {

    public static void main(String[] args) {

        // Header row containing the column names.
        String[] headers = {
                "id", "name", "salary"
        };

        // Data row containing the corresponding values.
        // The age column is intentionally missing.
        String[] data = {
                "101", "Rahul", "25000.50"
        };

        // Create an empty Student object.
        Student student = new Student();

        System.out.println("===== COLUMN MAPPER =====");

        try {

            // Populate the Student object using reflection.
            List<String> warnings =
                    ColumnMapper.populate(student, headers, data);

            // Display the populated student details.
            System.out.println("\n--- Student Details ---");
            student.display();

            // Display any missing-column or data warnings.
            System.out.println("\n--- Mapping Warnings ---");

            if (warnings.isEmpty()) {

                System.out.println("All columns mapped successfully.");

            } else {

                for (String warning : warnings) {
                    System.out.println("- " + warning);
                }
            }

        } catch (IllegalAccessException e) {

            System.out.println("Unable to access student fields.");
        }
    }
}
