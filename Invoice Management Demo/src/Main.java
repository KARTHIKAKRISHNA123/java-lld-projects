import java.util.*;

class Main {
    private static final Scanner scanner = new Scanner(System.in);

    private static final InvoiceService service = new InvoiceService();

    public static void main(String[] args) {

        while (true) {
            System.out.println("\n -----Invoice Management System-----");
            System.out.println("1. Add a Customer");
            System.out.println("2. Add an Invoice");
            System.out.println("3. Add Items to an Invoice");
            System.out.println("4. List all Customers");
            System.out.println("5. List All Invoices");
            System.out.println("6. List All Invoices of a Customer");
            System.out.println("7. Display the full Details of an Invoice");
            System.out.println("8. Exit");
            System.out.println("Choose an Option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addCustomer();
                case 2 -> addInvoice();
                case 3 -> addItemsToInvoice();
                case 4 -> service.listAllCustomers();
                case 5 -> service.listAllInvoices();
                case 6 -> listInvoicesOfCustomer();
                case 7 -> displayInvoiceDetails();
                case 8 -> {
                    System.out.println("Exiting the System. Goodbye!");
                    return;
                }
                default -> System.out.println("Invalid Choice. Please try again.");
            }
        }





    }

    private static void addCustomer() {
        System.out.println("Enter Customer ID: ");
        String id = scanner.nextLine();
        System.out.println("Enter Customer Name: ");
        String name = scanner.nextLine();
        service.addCustomer(id, name);
    }

    private static void addInvoice() {
        System.out.println("Enter Invoice Id: ");
        String id = scanner.nextLine();
        System.out.println("Enter Customer Id: ");
        String customerId = scanner.nextLine();
        service.addInvoice(id, customerId);
    }

    private static void addItemsToInvoice() {
        System.out.println("Enter Invoice Id: ");
        String invoiceId = scanner.nextLine();
        System.out.println("Enter Item name: ");
        String itemName = scanner.nextLine();
        System.out.println("Enter Item Quantity: ");
        int quantity = scanner.nextInt();
        System.out.println("Enter Item Price: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        service.addItemsToInvoice(invoiceId, itemName, quantity, price);
    }

    private static void listInvoicesOfCustomer() {
        System.out.println("Enter customer ID: ");
        String customerId = scanner.nextLine();
        service.listInvoicesOfCustomers(customerId);
    }

    private static void displayInvoiceDetails() {
        System.out.println("Enter Invoice Id: ");
        String invoiceId = scanner.nextLine();
        service.displayInvoiceDetails(invoiceId);
    }
}