package OOP_JAVA_25-26.Laptopmanagementsystem;

public public class Laptop extends Product {
    private final String processor;
    private final int ram;
    private final int storage;

    public Laptop(String brand, String model, String processor, int ram, int storage, double price, int quantity) {
        super(brand, model, price, quantity);
        this.processor = processor;
        this.ram = ram;
        this.storage = storage;
    }

    public String getProcessor() { return processor; }
    public int getRam() { return ram; }
    public int getStorage() { return storage; }

    @Override
    public void displayProductInfo() {
        System.out.println("Brand: " + getBrand());
        System.out.println("Model: " + getModel());
        System.out.println("Processor: " + processor);
        System.out.println("RAM: " + ram + "GB");
        System.out.println("Storage: " + storage + "GB");
        System.out.println("Price: Rs. " + getPrice());
        System.out.println("Quantity in stock: " + getQuantity());
    }
} 
    

