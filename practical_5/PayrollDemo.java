
package practical_5;

// Abstract parent class representing a general employee.
abstract class Employee {

    protected String name;
    protected int id;

    // Constructor to initialize common employee information.
    Employee(String name, int id) {
        this.name = name;
        this.id = id;
    }

    // Abstract method: each employee type calculates salary differently.
    abstract double monthlySalary();
}

// FullTime employee receives a fixed monthly salary.
class FullTime extends Employee {

    private double fixedSalary;

    // super() calls the parent constructor to initialize name and ID.
    FullTime(String name, int id, double fixedSalary) {
        super(name, id);
        this.fixedSalary = fixedSalary;
    }

    // Returns the fixed monthly salary.
    @Override
    double monthlySalary() {
        return fixedSalary;
    }
}

// PartTime employee is paid according to working hours.
class PartTime extends Employee {

    private double hours;
    private double rate;

    // Initialize employee details and hourly payment information.
    PartTime(String name, int id, double hours, double rate) {
        super(name, id);
        this.hours = hours;
        this.rate = rate;
    }

    // Salary = total working hours multiplied by hourly rate.
    @Override
    double monthlySalary() {
        return hours * rate;
    }
}

// Intern receives a fixed monthly stipend.
class Intern extends Employee {

    private double stipend;

    // Initialize common employee details using super().
    Intern(String name, int id, double stipend) {
        super(name, id);
        this.stipend = stipend;
    }

    // Returns the intern's monthly stipend.
    @Override
    double monthlySalary() {
        return stipend;
    }
}

// Main class to demonstrate employee payroll.
public class PayrollDemo {

    public static void main(String[] args) {

        // Creating a mixed array of different employee types.
        Employee[] employees = {
                new FullTime("Rahul", 101, 50000),
                new PartTime("Priya", 102, 80, 200),
                new Intern("Amit", 103, 10000),
                new FullTime("Neha", 104, 60000),
                new Intern("Karan", 105, 8000)
        };

        double totalSalary = 0;

        System.out.println("===== EMPLOYEE PAYROLL =====");

        // One loop processes all employee types.
        for (Employee e : employees) {

            // Polymorphism calls the correct salary method.
            double salary = e.monthlySalary();

            System.out.println("\nEmployee Name: " + e.name);
            System.out.println("Employee ID: " + e.id);
            System.out.printf("Monthly Salary: Rs. %.2f%n", salary);

            // Add the current salary to the total payroll.
            totalSalary += salary;

            // instanceof checks whether the employee is an Intern.
            if (e instanceof Intern) {
                System.out.println("Note: This employee is an intern.");
            }
        }

        // Display the total monthly payroll.
        System.out.println("\n----------------------------");
        System.out.printf("Total Payroll: Rs. %.2f%n", totalSalary);
    }
}
