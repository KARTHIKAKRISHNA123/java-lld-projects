public class Product {

    private String id;
    private String name;
    private double price;
    private int quantity;
    private String sellerId;

    public Product(String id, String name, double price, int quantity, String sellerId) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getId() {
        return id;
    }

    public String getSellerId() {
        return sellerId;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean updateQuantity(int amount) {
        if (this.quantity + amount >= 0) {
            this.quantity += amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return String.format("Product[ID: %s, Name: %s, Price: %.2f, Quantity: %d]", id, name, price, quantity);
    }
}
