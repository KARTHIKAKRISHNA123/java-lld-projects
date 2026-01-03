import java.util.*;
public class OrderService {
    private Map<String, Order> orders;
    private InventoryService inventoryService;

    public OrderService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
        this.orders = new HashMap<>();
    }

    public String placeOrder(Cart cart) {
        for (Map.Entry<String, Integer> item: cart.getItems().entrySet()) {
            Product product = inventoryService.getProduct(item.getKey());
            if (product == null || product.getQuantity() < item.getValue()) {
                return  null;
            }
        }

        String orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, cart.getUserId(), cart.getItems(), cart.getTotalAmount());
        orders.put(orderId, order);

        for (Map.Entry<String, Integer> item: cart.getItems().entrySet()) {
            inventoryService.updateQuantity(item.getKey(), -item.getValue());

        }

        return orderId;
    }

    public boolean processPayment(String orderId, double amount) {
        Order order = orders.get(orderId);
        if (order != null && !order.isPaid() &&  amount >= order.getTotalAmount()) {
            order.setPaid(true);
            return true;
        }
        return  false;
    }

    public List<Order> getUserOrders(String userId) {
        List<Order> userOrders = new ArrayList<>();

        for (Order order: orders.values()) {
            if (order.getUserId().equals(userId)) {
                userOrders.add(order);
            }
        }
        return  userOrders;
    }

}

class Order {
    private String orderId;
    private String userId;
    private Map<String, Integer> items;
    private double totalAmount;
    private boolean paid;

    public Order(String orderId, String userId, Map<String, Integer> items, double totalAmount) {
        this.paid = false;
        this.orderId = orderId;
        this.userId = userId;
        this.items = new HashMap<>(items);
        this.totalAmount = totalAmount;

    }

    public String getUserId() {
        return userId;
    }

    public String getOrderId() {
        return orderId;
    }

    public Map<String, Integer> getItems() {
        return new HashMap<>(items);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
