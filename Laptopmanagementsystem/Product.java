// Abstract base class for products
public abstract class Product {
    private final String brand;
    private final String model;
    private final double price;
    private int quantity;

    public Product(String brand, String model, double price, int quantity) {
        this.brand = brand;
        this.model = model;
        this.price = price;
        this.quantity = quantity;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Abstract method to display product information
    public abstract void displayProductInfo();
}