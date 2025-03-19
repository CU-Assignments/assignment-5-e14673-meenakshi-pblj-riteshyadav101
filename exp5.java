# Easy level:

import java.util.*;

public class AutoboxingUnboxingSum {

    // Method to calculate the sum of a list of integers
    public static int calculateSum(List<Integer> numbers) {
        int sum = 0;
        for (Integer number : numbers) {  // Autoboxing happens here
            sum += number;  // Unboxing happens here
        }
        return sum;
    }

    // Method to parse a list of strings into integers (using Integer.parseInt())
    public static List<Integer> parseStringsToIntegers(List<String> strings) {
        List<Integer> numbers = new ArrayList<>();
        for (String str : strings) {
            try {
                numbers.add(Integer.parseInt(str));  // Parsing string to Integer
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format: " + str);
            }
        }
        return numbers;
    }

    public static void main(String[] args) {
        // Sample list of strings
        List<String> stringNumbers = Arrays.asList("10", "20", "30", "40", "50");

        // Parse strings to integers
        List<Integer> numbers = parseStringsToIntegers(stringNumbers);

        // Calculate the sum of integers
        int sum = calculateSum(numbers);

        // Display the sum
        System.out.println("Sum of numbers: " + sum);
    }
}

# Medium level:

import java.io.*;

// Student class implements Serializable
class Student implements Serializable {
    private static final long serialVersionUID = 1L; // Serial version UID for version control
    private int id;
    private String name;
    private double gpa;

    // Constructor
    public Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }

    // Getter methods
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getGpa() {
        return gpa;
    }

    // Method to display student details
    public void displayDetails() {
        System.out.println("Student ID: " + id);
        System.out.println("Student Name: " + name);
        System.out.println("Student GPA: " + gpa);
    }
}

public class StudentSerialization {

    // Method to serialize a Student object and save it to a file
    public static void serializeStudent(Student student, String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(student);
            System.out.println("Student object serialized and saved to " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        }
    }

    // Method to deserialize a Student object from a file
    public static Student deserializeStudent(String filename) {
        Student student = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            student = (Student) in.readObject();
            System.out.println("Student object deserialized from " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("Class not found: " + e.getMessage());
        }
        return student;
    }

    public static void main(String[] args) {
        // Creating a Student object
        Student student = new Student(1, "John Doe", 3.8);

        // Serialize the student object to a file
        String filename = "student.ser";
        serializeStudent(student, filename);

        // Deserialize the student object from the file and display its details
        Student deserializedStudent = deserializeStudent(filename);
        if (deserializedStudent != null) {
            deserializedStudent.displayDetails();
        }
    }
}

# Hard level:

import java.io.*;
import java.util.*;

// Employee class implements Serializable for storing data to file
class Employee implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int id;
    private String designation;
    private double salary;

    // Constructor
    public Employee(String name, int id, String designation, double salary) {
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.salary = salary;
    }

    // Getter methods
    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public String getDesignation() {
        return designation;
    }

    public double getSalary() {
        return salary;
    }

    // Method to display employee details
    public void displayEmployeeDetails() {
        System.out.println("Employee ID: " + id);
        System.out.println("Employee Name: " + name);
        System.out.println("Employee Designation: " + designation);
        System.out.println("Employee Salary: " + salary);
        System.out.println("-------------------------------");
    }
}

public class EmployeeManagementApp {

    // Method to add an employee to the file
    public static void addEmployee(String filename, Employee employee) {
        List<Employee> employees = loadEmployees(filename);
        employees.add(employee);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(employees);
            System.out.println("Employee added successfully.");
        } catch (IOException e) {
            System.out.println("Error saving employee: " + e.getMessage());
        }
    }

    // Method to load all employees from the file
    public static List<Employee> loadEmployees(String filename) {
        List<Employee> employees = new ArrayList<>();
        try {
            File file = new File(filename);
            if (file.exists()) {
                try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
                    employees = (List<Employee>) in.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error loading employee data: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            System.out.println("Error loading employees: " + e.getMessage());
        }
        return employees;
    }

    // Method to display all employees
    public static void displayAllEmployees(String filename) {
        List<Employee> employees = loadEmployees(filename);
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
        } else {
            for (Employee employee : employees) {
                employee.displayEmployeeDetails();
            }
        }
    }

    // Method to show the menu and handle user input
    public static void showMenu(String filename) {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("Employee Management Menu:");
            System.out.println("1. Add an Employee");
            System.out.println("2. Display All Employees");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (choice) {
                case 1:
                    // Gather employee details
                    System.out.print("Enter Employee Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Employee ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume newline

                    System.out.print("Enter Employee Designation: ");
                    String designation = scanner.nextLine();

                    System.out.print("Enter Employee Salary: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine();  // Consume newline

                    Employee employee = new Employee(name, id, designation, salary);
                    addEmployee(filename, employee);
                    break;

                case 2:
                    displayAllEmployees(filename);
                    break;

                case 3:
                    System.out.println("Exiting program...");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (choice != 3);
        scanner.close();
    }

    public static void main(String[] args) {
        String filename = "employees.ser";  // File to store employee data
        showMenu(filename);  // Display menu and handle input
    }
}

