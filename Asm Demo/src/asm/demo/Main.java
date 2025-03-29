/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asm.demo;

import java.util.Scanner;


/**
 *
 * @author ASUS
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static My studentList = new My(100); // Assuming initial capacity of 100

    public static void main(String[] args) {
        while (true) {
            try {
                displayMenu();
                int choice = getIntInput("Enter your choice: ", 1, 7);
                
                switch (choice) {
                    case 1 -> addStudents();
                    case 2 -> editStudent();
                    case 3 -> deleteStudent();
                    case 4 -> displayStudents();
                    case 5 -> sortStudents();
                    case 6 -> searchStudent();
                    case 7 -> {
                        System.out.println("Exiting program...");
                        scanner.close();
                        return;
                    }
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                scanner.nextLine(); // Clear buffer
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nStudent Management System");
        System.out.println("1. Add Students");
        System.out.println("2. Edit Student");
        System.out.println("3. Delete Student");
        System.out.println("4. Display Students");
        System.out.println("5. Sort Students");
        System.out.println("6. Search Student");
        System.out.println("7. Exit");
    }

    private static int getIntInput(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a number between %d and %d.\n", min, max);
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static String getStringInput(String prompt, String regex, String errorMsg) {
        String input;
        do {
            System.out.print(prompt);
            input = scanner.nextLine();
            if (!input.matches(regex)) {
                System.out.println(errorMsg);
            }
        } while (!input.matches(regex));
        return input;
    }

    private static double getDoubleInput(String prompt, double min, double max) {
        while (true) {
            try {
                System.out.print(prompt);
                double input = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                if (input >= min && input <= max) {
                    return input;
                }
                System.out.printf("Please enter a number between %.1f and %.1f.\n", min, max);
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    private static void addStudents() {
        try {
            int numStudents = getIntInput("Enter the number of students to add: ", 1, Integer.MAX_VALUE);
            
            for (int i = 0; i < numStudents; i++) {
                System.out.println("\nEnter details for student " + (i + 1) + ":");
                
                String id = getUniqueStudentId();
                String name = getStringInput("Enter Student Name: ", "[a-zA-Z ]+", "Invalid Name! Please enter letters only.");
                double marks = getDoubleInput("Enter Student Marks (0-10): ", 0, 10);
                
                studentList.addStudent(new Student(id, name, marks));
                System.out.println("Student added successfully!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to add students: " + e.getMessage());
        }
    }

    private static String getUniqueStudentId() {
        String id;
        do {
            id = getStringInput("Enter Student ID: ", "\\d+", "Invalid ID! Please enter numbers only.");
            if (studentList.idExists(id)) {
                System.out.println("This ID already exists! Please enter a different ID.");
            }
        } while (studentList.idExists(id));
        return id;
    }

    private static void editStudent() {
        if (checkEmptyList()) return;
        
        try {
            String editId = getExistingStudentId("Enter Student ID to edit: ");
            String newName = getStringInput("Enter new name: ", "[a-zA-Z ]+", "Invalid Name! Please enter letters only.");
            double newMarks = getDoubleInput("Enter new marks (0-10): ", 0, 10);
            
            studentList.editStudent(editId, newName, newMarks);
            System.out.println("Student edited successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Failed to edit student: " + e.getMessage());
        }
    }

    private static void deleteStudent() {
        if (checkEmptyList()) return;
        
        try {
            String deleteId = getExistingStudentId("Enter Student ID to delete: ");
            studentList.deleteStudent(deleteId);
            System.out.println("Student deleted successfully!");
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete student: " + e.getMessage());
        }
    }

    private static void displayStudents() {
        if (checkEmptyList()) return;
        
        try {
            studentList.displayStudents();
        } catch (Exception e) {
            throw new RuntimeException("Failed to display students: " + e.getMessage());
        }
    }

    private static void sortStudents() {
        if (checkEmptyList()) return;
        
        try {
            int sortChoice = getIntInput("Choose Sorting Method: 1. QuickSort(decreasing) 2. BubbleSort(ascending)\n", 1, 2);
            
            if (sortChoice == 1) {
                studentList.quickSort();
                System.out.println("Students sorted successfully using QuickSort!");
            } else {
                studentList.bubbleSort();
                System.out.println("Students sorted successfully using BubbleSort!");
            }
            studentList.displayStudents();
        } catch (Exception e) {
            throw new RuntimeException("Failed to sort students: " + e.getMessage());
        }
    }

    private static void searchStudent() {
        if (checkEmptyList()) return;
        
        try {
            int searchChoice = getIntInput("Choose Search Method: 1. Binary Search 2. Linear Search\n", 1, 2);
            String searchId = getStringInput("Enter Student ID to search: ", "\\d+", "Invalid ID! Please enter numbers only.");
            
            Student foundStudent = (searchChoice == 1) ? studentList.binarySearchSSS(searchId) : studentList.linearSearch(searchId);
            
            if (foundStudent != null) {
                System.out.println("Found: " + foundStudent);
                System.out.println("Search successful!");
            } else {
                System.out.println("Student not found!");
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to search student: " + e.getMessage());
        }
    }

    private static String getExistingStudentId(String prompt) {
        String id;
        do {
            id = getStringInput(prompt, "\\d+", "Invalid ID! Please enter numbers only.");
            if (!studentList.idExists(id)) {
                System.out.println("Student ID not found. Please try again.");
            }
        } while (!studentList.idExists(id));
        return id;
    }

    private static boolean checkEmptyList() {
        if (studentList.isEmpty()) {
            System.out.println("No students in the list. Please add students first.");
            return true;
        }
        return false;
    }
}
    


        