package OOP_JAVA_25-26.Laptopmanagementsystem;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class LaptopManagementSystem {
    private final ArrayList<Laptop> laptops = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "laptops.csv";

    // Constructor loads previously saved data (continues from last session)
    public LaptopManagementSystem() {
        loadFromFile();
    }

    // --- Safe input readers (handle non-numeric, negatives) ---
    private int readIntNonNegative(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                int val = Integer.parseInt(scanner.nextLine().trim());
                if (val < 0) {
                    System.out.println("Value cannot be negative. Please try again.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter an integer.");
            }
        }
    }

    private double readDoubleNonNegative(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                double val = Double.parseDouble(scanner.nextLine().trim());
                if (val < 0) {
                    System.out.println("Value cannot be negative. Please try again.");
                    continue;
                }
                return val;
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please enter a valid amount.");
            }
        }
    }

    private String readNonEmptyLine(String prompt) {
        while (true) {
            System.out.print(prompt);
            String s = scanner.nextLine();
            if (s == null) s = "";
            s = s.trim();
            if (!s.isEmpty()) return s;
            System.out.println("This field cannot be empty. Please try again.");
        }
    }

    private String escape(String s) { return (s == null) ? "" : s.replace(",", " "); }

    // --- File persistence ---
    private void loadFromFile() {
        File f = new File(FILE_NAME);
        if (!f.exists()) return;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(",", -1);
                if (parts.length < 7) continue;
                try {
                    String brand = parts[0];
                    String model = parts[1];
                    String processor = parts[2];
                    int ram = Integer.parseInt(parts[3]);
                    int storage = Integer.parseInt(parts[4]);
                    double price = Double.parseDouble(parts[5]);
                    int qty = Integer.parseInt(parts[6]);
                    if (ram < 0 || storage < 0 || price < 0 || qty < 0) continue;
                    laptops.add(new Laptop(brand, model, processor, ram, storage, price, qty));
                } catch (Exception parseEx) {
                    System.out.println("Skipping bad line in data: " + parseEx.getMessage());
                }
            }
            if (!laptops.isEmpty()) {
                System.out.println("Loaded " + laptops.size() + " laptop(s) from " + FILE_NAME + ".");
            }
        } catch (IOException e) {
            System.out.println("Failed to load saved data: " + e.getMessage());
        }
    }

    public void saveToFile() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME, false))) {
            for (Laptop l : laptops) {
                pw.printf("%s,%s,%s,%d,%d,%.2f,%d%n",
                        escape(l.getBrand()), escape(l.getModel()), escape(l.getProcessor()),
                        l.getRam(), l.getStorage(), l.getPrice(), l.getQuantity());
            }
            System.out.println("Data saved to " + FILE_NAME + ".");
        } catch (IOException e) {
            System.out.println("Failed to save data: " + e.getMessage());
        }
    }

    // --- Features ---
    public void addLaptop() {
        try {
            String brand = readNonEmptyLine("Enter laptop brand: ");
            String model = readNonEmptyLine("Enter laptop model: ");
            String processor = readNonEmptyLine("Enter processor: ");
            int ram = readIntNonNegative("Enter RAM (in GB): ");
            int storage = readIntNonNegative("Enter storage (in GB): ");
            double price = readDoubleNonNegative("Enter price: ");
            int qty = readIntNonNegative("Enter quantity in stock: ");

            laptops.add(new Laptop(brand, model, processor, ram, storage, price, qty));
            System.out.println("Laptop added successfully!");
        } catch (Exception e) {
            System.out.println("Failed to add laptop: " + e.getMessage());
        }
    }

    public void viewAllLaptops() {
        if (laptops.isEmpty()) {
            System.out.println("No laptops available.");
            return;
        }
        for (int i = 0; i < laptops.size(); i++) {
            System.out.println("\nLaptop " + (i + 1) + ":");
            laptops.get(i).displayProductInfo();
        }
    }

    public void removeLaptop() {
        System.out.print("Enter model to remove: ");
        String modelToRemove = scanner.nextLine();
        boolean removed = false;
        try {
            removed = laptops.removeIf(l -> l.getModel().equalsIgnoreCase(modelToRemove));
        } catch (Exception e) {
            System.out.println("Error while removing: " + e.getMessage());
        }
        System.out.println(removed ? "Laptop removed successfully." : "Laptop not found.");
    }

    public void searchLaptop() {
        System.out.print("Enter brand to search (or leave blank for all): ");
        String brand = scanner.nextLine().trim();
        try {
            List<Laptop> results = laptops.stream()
                    .filter(l -> brand.isEmpty() || l.getBrand().equalsIgnoreCase(brand))
                    .collect(Collectors.toList());
            if (results.isEmpty()) System.out.println("No laptops found.");
            else results.forEach(Laptop::displayProductInfo);
        } catch (Exception e) {
            System.out.println("Search error: " + e.getMessage());
        }
    }

    public void updateAvailabilityStatus() {
        System.out.print("Enter model to update: ");
        String model = scanner.nextLine();
        for (Laptop laptop : laptops) {
            if (laptop.getModel().equalsIgnoreCase(model)) {
                int newQty = readIntNonNegative("Enter new quantity: ");
                laptop.setQuantity(newQty);
                System.out.println("Quantity updated successfully!");
                return;
            }
        }
        System.out.println("Laptop not found.");
    }

    public void sortLaptopsByPrice() {
        if (laptops.isEmpty()) {
            System.out.println("No laptops to sort.");
            return;
        }
        try {
            laptops.sort(Comparator.comparingDouble(Laptop::getPrice));
            System.out.println("Laptops sorted by price:");
            viewAllLaptops();
        } catch (Exception e) {
            System.out.println("Sort failed: " + e.getMessage());
        }
    }

    public void calculateAveragePrice() {
        if (laptops.isEmpty()) {
            System.out.println("No laptops available.");
            return;
        }
        try {
            double avg = laptops.stream().mapToDouble(Laptop::getPrice).average().orElse(0);
            System.out.printf("Average laptop price: Rs. %.2f%n", avg);
        } catch (Exception e) {
            System.out.println("Failed to compute average: " + e.getMessage());
        }
    }

    public void countLaptopsByBrand() {
        System.out.print("Enter brand name: ");
        String brand = scanner.nextLine().trim();
        try {
            long count = laptops.stream().filter(l -> l.getBrand().equalsIgnoreCase(brand)).count();
            System.out.println("Number of laptops of brand " + brand + ": " + count);
        } catch (Exception e) {
            System.out.println("Count failed: " + e.getMessage());
        }
    }

    public void generateReport() {
        if (laptops.isEmpty()) {
            System.out.println("No data available.");
            return;
        }
        try {
            System.out.println("\n--- Summary Report ---");
            System.out.println("Total Laptops: " + laptops.size());
            System.out.println("Total Stock Value: Rs. " +
                    laptops.stream().mapToDouble(l -> l.getPrice() * l.getQuantity()).sum());
        } catch (Exception e) {
            System.out.println("Failed to generate report: " + e.getMessage());
        }
    }

    // Compare two laptops by specifications (brand, processor, RAM, storage)
    public void compareLaptops() {
        if (laptops.size() < 2) {
            System.out.println("At least two laptops are required to compare.");
            return;
        }
        System.out.print("Enter first laptop model: ");
        String model1 = scanner.nextLine().trim();
        System.out.print("Enter second laptop model: ");
        String model2 = scanner.nextLine().trim();

        Laptop laptop1 = null, laptop2 = null;
        for (Laptop l : laptops) {
            if (l.getModel().equalsIgnoreCase(model1)) laptop1 = l;
            if (l.getModel().equalsIgnoreCase(model2)) laptop2 = l;
        }

        if (laptop1 == null || laptop2 == null) {
            System.out.println("One or both models not found.");
            return;
        }

        System.out.println("\n--- Laptop Comparison ---");
        System.out.printf("%-15s %-20s %-20s%n", "Specification", laptop1.getModel(), laptop2.getModel());
        System.out.println("-----------------------------------------------------------");
        System.out.printf("%-15s %-20s %-20s%n", "Brand", laptop1.getBrand(), laptop2.getBrand());

        // Processor line with evaluation (ADDED)
        String betterProcessor = compareProcessorPerformance(laptop1.getProcessor(), laptop2.getProcessor());
        System.out.printf("%-15s %-20s %-20s (%s)%n", "Processor",
                laptop1.getProcessor(), laptop2.getProcessor(), betterProcessor);

        // Compare RAM
        String betterRAM;
        if (laptop1.getRam() > laptop2.getRam()) {
            betterRAM = laptop1.getModel() + " has more RAM";
        } else if (laptop2.getRam() > laptop1.getRam()) {
            betterRAM = laptop2.getModel() + " has more RAM";
        } else {
            betterRAM = "Both have same RAM";
        }
        System.out.printf("%-15s %-20d %-20d (%s)%n", "RAM (GB)", laptop1.getRam(), laptop2.getRam(), betterRAM);

        // Compare Storage
        String betterStorage;
        if (laptop1.getStorage() > laptop2.getStorage()) {
            betterStorage = laptop1.getModel() + " has more storage";
        } else if (laptop2.getStorage() > laptop1.getStorage()) {
            betterStorage = laptop2.getModel() + " has more storage";
        } else {
            betterStorage = "Both have same storage";
        }
        System.out.printf("%-15s %-20d %-20d (%s)%n", "Storage (GB)", laptop1.getStorage(), laptop2.getStorage(), betterStorage);
    }

    // --- Helper: Compare processor ranking heuristically (ADDED) ---
    private String compareProcessorPerformance(String p1, String p2) {
        String p1Lower = p1 == null ? "" : p1.toLowerCase();
        String p2Lower = p2 == null ? "" : p2.toLowerCase();

        int s1 = processorScore(p1Lower);
        int s2 = processorScore(p2Lower);

        if (s1 > s2) return p1 + " is better";
        else if (s2 > s1) return p2 + " is better";
        else return "Both processors are comparable";
    }

    // --- Helper: score common CPU names (Intel i3/i5/i7/i9, AMD Ryzen 3/5/7/9, simple gen hints) (ADDED) ---
    private int processorScore(String proc) {
        int score = 0;

        // Intel Core
        if (proc.contains("i9")) score += 90;
        else if (proc.contains("i7")) score += 70;
        else if (proc.contains("i5")) score += 50;
        else if (proc.contains("i3")) score += 30;

        // AMD Ryzen
        if (proc.contains("ryzen 9")) score += 90;
        else if (proc.contains("ryzen 7")) score += 70;
        else if (proc.contains("ryzen 5")) score += 50;
        else if (proc.contains("ryzen 3")) score += 30;

        // Simple generation cues like "13th", "12th"
        for (int gen = 3; gen <= 20; gen++) {
            if (proc.contains(gen + "th")) { score += gen; break; }
        }

        // Basic uplift for known mobile suffixes (very rough heuristic)
        if (proc.contains("hx") || proc.contains("hk")) score += 6;
        if (proc.contains("h")) score += 4;
        if (proc.contains("u")) score += 1;

        return score;
    }
}