
package practical_7;

import java.lang.annotation.*;
import java.lang.reflect.Method;

// Custom annotation used to mark methods that should be executed.
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {
}

// Class containing methods that act as test cases.
class MyTests {

    // This method will be executed because it has @Run.
    @Run
    public void testAddition() {
        int result = 10 + 20;
        System.out.println("Addition Test: " + result);
    }

    // This method will also be executed.
    @Run
    public void testSubtraction() {
        int result = 50 - 20;
        System.out.println("Subtraction Test: " + result);
    }

    // This method will NOT be executed because it has no @Run.
    public void displayMessage() {
        System.out.println("This method should not execute.");
    }

    // Another test method marked with @Run.
    @Run
    public void testMultiplication() {
        int result = 5 * 4;
        System.out.println("Multiplication Test: " + result);
    }

    // This method will also be ignored by the runner.
    public void normalMethod() {
        System.out.println("Normal method executed.");
    }
}

// Class responsible for discovering and executing test methods.
class MiniTestRunner {

    // Finds and invokes all methods marked with @Run.
    public static int executeTests(Object testObject)
            throws Exception {

        int count = 0;

        // Get all methods declared inside the test object's class.
        Method[] methods =
                testObject.getClass().getDeclaredMethods();

        // Examine each method.
        for (Method method : methods) {

            // Check whether the method has the @Run annotation.
            if (method.isAnnotationPresent(Run.class)) {

                // Ensure that only no-argument methods are executed.
                if (method.getParameterCount() == 0) {

                    System.out.println(
                            "\nExecuting: " + method.getName()
                    );

                    // Invoke the selected method on the test object.
                    method.invoke(testObject);

                    // Increase the count after successful execution.
                    count++;

                } else {

                    System.out.println(
                            "Skipping " + method.getName()
                                    + ": method must have no arguments."
                    );
                }
            }
        }

        // Return the total number of successfully executed methods.
        return count;
    }
}

// Main class to demonstrate the mini test runner.
public class MiniTestRunnerDemo {

    public static void main(String[] args) {

        // Create an object containing test methods.
        MyTests tests = new MyTests();

        System.out.println("===== MINI TEST RUNNER =====");

        try {

            // Execute only methods marked with @Run.
            int total = MiniTestRunner.executeTests(tests);

            // Display the total number of executed methods.
            System.out.println("\n----------------------------");
            System.out.println("Total tests executed: " + total);

        } catch (Exception e) {

            // Handle errors that occur during reflection or invocation.
            System.out.println("Error while running tests: "
                    + e.getMessage());
        }
    }
}
