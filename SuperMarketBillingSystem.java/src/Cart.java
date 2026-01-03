import java.util.*;

public class Cart {
    private String userId;
    private Map<String, Integer> items; // productId -> Quantity
    private double totalAmount;

    public Cart(String userId) {
        this.userId = userId;
        this.items = new HashMap<>();
        this.totalAmount = 0.0;

    }

    public boolean addItem(Product product, int quantity) {
        if (product.getQuantity() >= quantity) {
            items.put(product.getId(), items.getOrDefault(product.getId(), 0) + quantity);
            totalAmount += product.getPrice() * quantity;
            return true;
        }

        return false;
    }

    public boolean removeItem(Product product, int quantity) {
        if (items.containsKey(product.getId())) {
            int currentQty = items.get(product.getId());
            if (currentQty >= quantity) {
                if (currentQty == quantity) {
                    items.remove(product.getId());
                }
                else {
                    items.put(product.getId(), currentQty - quantity);
                }
                totalAmount -= product.getPrice() * quantity;
                return  true;

            }
        }
        return  false;
    }

    public void clearCart() {
        items.clear();
        totalAmount = 0.0;
    }

    public Map<String, Integer> getItems() {
        return new HashMap<>(items);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getUserId() {
        return userId;
    }
}