
package practical_7;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

// Custom annotation to ensure that a field is not blank.
// It can only be applied to fields.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
}

// Custom annotation to specify the maximum allowed length of a field.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value(); // Stores the maximum permitted number of characters.
}

// SignupForm class containing fields with validation annotations.
class SignupForm {

    @NotBlank
    @MaxLength(20)
    private String username;

    @NotBlank
    @MaxLength(30)
    private String email;

    @NotBlank
    @MaxLength(10)
    private String password;

    // Constructor to initialize form details.
    SignupForm(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

// Class responsible for validating objects using reflection.
class FormValidator {

    // Checks all annotated fields and returns validation errors.
    public static List<String> validate(Object object)
            throws IllegalAccessException {

        List<String> errors = new ArrayList<>();

        // Get all declared fields of the object's class.
        Field[] fields = object.getClass().getDeclaredFields();

        // Examine each field one by one.
        for (Field field : fields) {

            // Allow access to private fields using reflection.
            field.setAccessible(true);

            // Read the current field value from the object.
            Object value = field.get(object);

            // Check whether the field has the @NotBlank annotation.
            if (field.isAnnotationPresent(NotBlank.class)) {

                // A null value or a string containing only spaces is invalid.
                if (value == null || value.toString().trim().isEmpty()) {

                    errors.add(field.getName()
                            + " must not be blank.");
                }
            }

            // Check whether the field has the @MaxLength annotation.
            if (field.isAnnotationPresent(MaxLength.class)) {

                // Read the maximum length specified in the annotation.
                MaxLength annotation =
                        field.getAnnotation(MaxLength.class);

                int maxLength = annotation.value();

                // Check whether the value exceeds the permitted length.
                if (value != null &&
                        value.toString().length() > maxLength) {

                    errors.add(field.getName()
                            + " must not exceed "
                            + maxLength + " characters.");
                }
            }
        }

        // Return all validation errors found.
        return errors;
    }
}

// Main class to demonstrate annotation-based validation.
public class FormValidatorDemo {

    public static void main(String[] args) {

        // Creating a signup form with some invalid values.
        SignupForm form = new SignupForm(
                "   ",
                "student@gmail.com",
                "verylongpassword123"
        );

        System.out.println("===== SIGNUP FORM VALIDATOR =====");

        try {

            // Validate the form using reflection.
            List<String> errors = FormValidator.validate(form);

            // Display the validation results.
            if (errors.isEmpty()) {

                System.out.println("Form is valid!");

            } else {

                System.out.println("Form contains validation errors:");

                // Print every validation error.
                for (String error : errors) {
                    System.out.println("- " + error);
                }
            }

        } catch (IllegalAccessException e) {

            // Handle errors while accessing fields through reflection.
            System.out.println("Unable to access form fields.");
        }
    }
}
