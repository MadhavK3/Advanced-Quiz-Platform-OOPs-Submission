package OOP_JAVA_25-26.Laptopmanagementsystem;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        LaptopManagementSystem system = new LaptopManagementSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n--- Laptop Management System ---");
            System.out.println("1. Add Laptop");
            System.out.println("2. Search Laptop");
            System.out.println("3. Remove Laptop");
            System.out.println("4. View All Laptops");
            System.out.println("5. Sort Laptops by Price");
            System.out.println("6. Calculate Average Price of Laptops");
            System.out.println("7. Update Laptop Availability Status");
            System.out.println("8. Count Laptops by Brand");
            System.out.println("9. Generate Summary Report");
            System.out.println("10. Compare Two Laptops");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine().trim());
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a number 1-11.");
                choice = -1;
            }

            switch (choice) {
                case 1 -> system.addLaptop();
                case 2 -> system.searchLaptop();
                case 3 -> system.removeLaptop();
                case 4 -> system.viewAllLaptops();
                case 5 -> system.sortLaptopsByPrice();
                case 6 -> system.calculateAveragePrice();
                case 7 -> system.updateAvailabilityStatus();
                case 8 -> system.countLaptopsByBrand();
                case 9 -> system.generateReport();
                case 10 -> system.compareLaptops();
                case 11 -> {
                    system.saveToFile();
                    System.out.println("Thank you for using Laptop Management System!");
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 11);

        scanner.close();
    }
}