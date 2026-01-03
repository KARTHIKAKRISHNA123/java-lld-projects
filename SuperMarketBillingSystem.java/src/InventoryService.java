import java.util.*;

public class InventoryService {
    private Map<String, Product> products;

    public InventoryService() {
        this.products = new HashMap<>();

    }

    public boolean addItem(Product product) {
        if (!products.containsKey(product.getId())) {
            products.put(product.getId(), product);
            return true;
        }
        return false;
    }

    public boolean updateItem(String productId, double price, int quantity) {
        if (products.containsKey(productId)) {
            Product product = products.get(productId);
            product.setPrice(price);
            product.setQuantity(quantity);
            return true;
        }
        return false;
    }

    public Product getProduct(String productID) {
        return products.get(productID);
    }

    public List<Product> listInventory() {
        return new ArrayList<>(products.values());
    }

    public boolean updateQuantity(String productId, int quantity) {
        if (products.containsKey(productId)) {
            return  products.get(productId).updateQuantity(quantity);
        }
        return false;
    }



}
