import java.sql.SQLOutput;
import java.util.*;

public class OnlineShopping {
    private Map<String, User> users;
    private Map<String, Cart> carts;
    private InventoryService inventoryService;
    private OrderService orderService;
    private Scanner scanner;
    private User currentUser;


    public OnlineShopping() {
        this.users = new HashMap<>();
        this.carts = new HashMap<>();

        this.inventoryService = new InventoryService();
        this.orderService = new OrderService(inventoryService);
        this.scanner = new Scanner(System.in);
        this.currentUser = null;
    }

    public void start() {
        while (true) {
            if (currentUser == null) {
                showLoginMenu();
            } else if (currentUser.getRole().equals("seller")) {
                showSellerMenu();
            }
            else {
                showBuyerMenu();
            }
        }
    }

    private void showLoginMenu() {
        System.out.println("\n=== Welcome To Online Shopping ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Choose an Option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();


        switch (choice) {
            case 1:
                login();
                break;
            case 2:
                register();
                break;
            case 3:
                System.out.println("Thankyou for using online shopping or Super Market Billing");
                System.exit(0);
            default:
                System.out.println("Invalid Option!");

        }
    }

    private void showSellerMenu() {
        System.out.println("\n=== Seller Menu ===");
        System.out.println("1. Add Item");
        System.out.println("2. Update Item");
        System.out.println("3. View Inventory");
        System.out.println("4. Logout");
        System.out.println("Choose an option");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                addItem();
                break;
            case 2:
                updateItem();
                break;
            case 3:
                viewInventory();
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("Invalid Option!");
        }
    }

    private void showBuyerMenu() {
        System.out.println("\n===Buyer Menu===");
        System.out.println("1. View Products");
        System.out.println("2. Add to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Checkout");
        System.out.println("5. View Orders");
        System.out.println("6. Logout");
        System.out.print("Enter an Option: ");

        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                viewInventory();
                break;
            case 2:
                addToCart();
                break;
            case 3:
                viewCart();
                break;
            case 4:
                checkout();
                break;
            case 5:
                viewOrders();
                break;
            case 6:
                logout();
                break;

            default:
                System.out.println("Invalid Option!");

        }
    }

    private void register() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();


        if (users.containsKey(username)) {
            System.out.println("Username Already exists");
            return;
        }

        System.out.print("Enter Password");
        String password = scanner.nextLine();

        if (!User.isValidPassword(password)) {
            System.out.println("Password must be 8 characters long, one special character, one uppercase letter and lowercase letter");
            return;
        }

        System.out.println("Enter role (buyer/ seller): ");
        String role = scanner.nextLine().toLowerCase();

        if (!role.equals("buyer") && !role.equals("seller")) {
            System.out.println("Invalid role");
            return;
        }

        User user = new User(username, password, role);
        users.put(username, user);
        System.out.println("Registration Successful ");


    }

    private void login() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        User user = users.get(username);
        if (user != null && user.validatePassword(password)) {
            currentUser = user;
            currentUser.setLoggedIn(true);
            System.out.println("Login Successful!");

        }

        else {
            System.out.println("Invalid username or password!");
        }
    }

    private void logout() {
        if (currentUser != null) {
            currentUser.setLoggedIn(false);
            currentUser = null;
            System.out.println("Logged out Successfully!");


        }
    }


    private void addItem() {
        System.out.println("Enter Product Id: ");
        String id = scanner.nextLine();

        System.out.println("Enter Product Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Price: ");
        double price = scanner.nextDouble();
        System.out.println("Enter New Quantity: ");

        int quantity = scanner.nextInt();
        scanner.nextLine();

        Product product = new Product(id, name, price, quantity, currentUser.getUsername());

        if (inventoryService.addItem(product)) {
            System.out.println("Product Added Successfully!");
        }

        else {
            System.out.println("Product ID already exists");
        }



    }

    private void updateItem() {
        System.out.println("Enter Product Id: ");
        String id = scanner.nextLine();

        System.out.println("Enter Price: ");
        double price = scanner.nextDouble();

        System.out.println("Enter New Quantity: ");

        int quantity = scanner.nextInt();
        scanner.nextLine();

        if (inventoryService.updateItem(id, price, quantity)) {
            System.out.println("Product updated successfully!");
        }

        else {
            System.out.println("Product Not Found!");
        }

    }

    private void viewInventory() {
        List<Product> products = inventoryService.listInventory();
        if (products.isEmpty()) {
            System.out.println("No Products Available!" );
            return;
        }

        System.out.println("\nAvailable Products: " );
        for (Product product: products) {
            System.out.println(product);
        }
    }

    private void addToCart() {
        System.out.println("Enter Product Id: ");
        String productId = scanner.nextLine();
        System.out.println("Enter Quantity: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        Product product = inventoryService.getProduct(productId);
        if (product == null) {
            System.out.println("Product not found! ");
            return;
        }

        Cart cart = carts.computeIfAbsent(currentUser.getUsername(), k -> new Cart(k));
        if (cart.addItem(product, quantity)) {
            System.out.println("Product Added to the cart!");
        }
        else {
            System.out.println("Insufficient stock!");
        }
    }

    private void viewCart() {
        Cart cart = carts.get(currentUser.getUsername());
        if (cart == null || cart.getItems().isEmpty()) {
            System.out.println("Cart is Empty!");
            return;
        }

        System.out.println("\n Your Cart: ");
        for (Map.Entry<String, Integer> item: cart.getItems().entrySet()) {
            Product product = inventoryService.getProduct(item.getKey());
            System.out.printf("%s - Quantity: %d - Total: $%.2f%n", product.getName(), item.getValue(), product.getPrice() * item.getValue());
        }

        System.out.printf("Total Amount: $%.2f%n", cart.getTotalAmount());
    }

    private void checkout() {
        Cart cart = carts.get(currentUser.getUsername());
        if (cart == null || cart.getItems().isEmpty()) {
            System.out.println("The cart is Empty");
            return;
        }

        System.out.printf("Total amount $%.2f%n: ", cart.getTotalAmount() );
        System.out.print("Enter payment Amount: ");
        double payment = scanner.nextDouble();
        scanner.nextLine();

        String orderId = orderService.placeOrder(cart);
        if (orderId != null && orderService.processPayment(orderId,payment)) {
            System.out.println("Order placed Successfully!");
            System.out.println("Order ID: " + orderId);
            cart.clearCart();

        }
        else {
            System.out.println("Order failed! Please check your cart and payment");
        }
    }

    private void viewOrders() {
        List<Order> orders = orderService.getUserOrders(currentUser.getUsername());
        if (orders.isEmpty()) {
            System.out.println("No Orders Found!");
            return;
        }

        System.out.println("\n Your Orders: ");
        for (Order order: orders) {
            System.out.printf("Order ID: %s - Total: $%.2f - status: %s%n", order.getOrderId(), order.getTotalAmount(), order.isPaid() ? "paid" : "Not paid");
        }
    }

    public static void main(String[] args) {
        OnlineShopping shop = new OnlineShopping();
        shop.start();
    }


}
