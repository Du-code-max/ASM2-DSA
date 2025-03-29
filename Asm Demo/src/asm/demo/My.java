/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package asm.demo;





/**
 *
 * @author ASUS
 */
public class My {
    private Node head; // Primary storage in linked list
    private Student[] studentArray; // Temporary array for sorting/searching
    private int size; // Number of students

    public My(int capacity) {
        this.head = null;
        this.studentArray = new Student[capacity];
        this.size = 0;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void addStudent(Student student) {
        // Add to linked list only
        Node newNode = new Node(student);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        size++;
    }

    public void editStudent(String id, String newName, double newMarks) {
        // Edit in linked list only
        Node current = head;
        while (current != null) {
            if (current.data.id.equals(id)) {
                current.data.setName(newName);
                current.data.setMarks(newMarks);
                break;
            }
            current = current.next;
        }
    }

    public void deleteStudent(String id) {
        // Delete from linked list only
        if (head == null) return;
        if (head.data.id.equals(id)) {
            head = head.next;
            size--;
            return;
        }
        Node current = head;
        while (current.next != null && !current.next.data.id.equals(id)) {
            current = current.next;
        }
        if (current.next != null) {
            current.next = current.next.next;
            size--;
        }
    }
    
    public void displayStudents() {
        Node current = head;
        while (current != null) {
            System.out.println(current.data);
            current = current.next;
        }
    }
    
    public boolean idExists(String id) {
    Node current = head;
    while (current != null) {
        if (current.data.id.equals(id)) {
            return true;  // ID found
        }
        current = current.next;
    }
    return false;  // ID not found
}
    
    // Helper method to copy linked list to array for sorting/searching
    private void prepareArray() {
        studentArray = new Student[size];
        Node current = head;
        int index = 0;
        while (current != null) {
            studentArray[index++] = current.data;
            current = current.next;
        }
    }
    
    public void quickSort() {
        prepareArray(); // Copy data from linked list to array
        quickSortHelper(0, size - 1);
        // Optional: copy back to linked list if needed
        updateLinkedListFromArray();
    }

    private void quickSortHelper(int left, int right) {
        if (left < right) {
            int pivotIndex = partition(left, right);
            quickSortHelper(left, pivotIndex - 1);
            quickSortHelper(pivotIndex + 1, right);
        }
    }

    private int partition(int left, int right) {
        double pivot = studentArray[right].getMarks();
        int i = left - 1;
        for (int j = left; j < right; j++) {
            if (studentArray[j].getMarks() >= pivot) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, right);
        return i + 1;
    }

    private void swap(int i, int j) {
        Student temp = studentArray[i];
        studentArray[i] = studentArray[j];
        studentArray[j] = temp;
    }
    
    public void bubbleSort() {
        prepareArray(); // Copy data from linked list to array
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (studentArray[j].getMarks() > studentArray[j + 1].getMarks()) {
                    swap(j, j + 1);
                }
            }
        }
        // Optional: copy back to linked list if needed
        updateLinkedListFromArray();
    }
    
    // Optional: update linked list from sorted array
    private void updateLinkedListFromArray() {
        if (studentArray == null || studentArray.length == 0) return;
        
        head = new Node(studentArray[0]);
        Node current = head;
        for (int i = 1; i < studentArray.length; i++) {
            current.next = new Node(studentArray[i]);
            current = current.next;
        }
    }
    
    public Student binarySearchSSS(String id) {
        prepareArray(); // Copy data from linked list to array
        // First sort the array by ID for binary search
        sortArrayById();
        
        int left = 0, right = size - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            int cmp = studentArray[mid].id.compareTo(id);
            if (cmp == 0) return studentArray[mid];
            else if (cmp < 0) left = mid + 1;
            else right = mid - 1;
        }
        return null;
    }
    
    private void sortArrayById() {
        // Simple bubble sort by ID
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i - 1; j++) {
                if (studentArray[j].id.compareTo(studentArray[j + 1].id) > 0) {
                    swap(j, j + 1);
                }
            }
        }
    }
    
    public Student linearSearch(String id) {
        prepareArray(); // Copy data from linked list to array
        for (int i = 0; i < size; i++) {
            if (studentArray[i].id.equals(id)) {
                return studentArray[i];
            }
        }
        return null;
    }
}