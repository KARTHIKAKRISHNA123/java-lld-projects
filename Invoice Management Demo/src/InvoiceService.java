import java.util.*;
class InvoiceService {
    private final Map<String, Customer> customers = new HashMap<>();
    private final Map<String, Invoice> invoices = new HashMap<>();

    public void addCustomer(String id, String name) {
        customers.put(id, new Customer(id, name));
        System.out.println("Customers added Successfully!");
    }

    public void addInvoice(String id, String customerId) {
        if (!customers.containsKey(customerId)) {
            System.out.println("Customer Not Present!");
            return;
        }

        invoices.put(id, new Invoice(id, customerId));
        System.out.println("Invoice added Successfully");
    }

    public void addItemsToInvoice(String invoiceId, String itemName, int quantity, double price) {
        Invoice invoice = invoices.get(invoiceId);
        if (invoice == null) {
            System.out.println("Invoice not Found!");
            return;
        }

        invoice.items.add(new Item(itemName, quantity, price));
        System.out.println("Item added Successfully!");
    }

    public void listAllCustomers() {
        if (customers.isEmpty()) {
            System.out.println("No Customers Found!");
            return;
        }

        System.out.println("----Customers----");
        for (Customer customer: customers.values()) {
            System.out.println("ID: " + customer.id + ", Name: " + customer.name);

        }
    }

    public void listAllInvoices() {
        if (invoices.isEmpty()) {
            System.out.println("No Invoices Found!");
            return;
        }

        System.out.println("----Invoice----");
        for (Invoice invoice: invoices.values()) {
            System.out.println("Invoices ID: " + invoice.id + ", Customer ID: " + invoice.customerId);
        }
    }

    public void listInvoicesOfCustomers(String customerId) {
        boolean found = false;
        System.out.println("---Invoices For Customer ID---: " + customerId + "---");

        for (Invoice invoice: invoices.values()) {
            if (invoice.customerId.equals(customerId)) {
                System.out.println("Invoice ID: " + invoice.id);
                found = true;

            }

            if (!found) {
                System.out.println("No Invoices found for this customer.");
            }
        }
    }

    public void displayInvoiceDetails(String invoiceId) {
        Invoice invoice = invoices.get(invoiceId);
        if (invoice == null) {
            System.out.println("Invoice Not Found!");
            return;
        }
        System.out.println("---Invoice Details---");
        System.out.println("Invoice ID: " + invoice.id);
        System.out.println("Customer ID: " + invoice.customerId);
        System.out.println("Items: ");
        for (Item item: invoice.items) {
            System.out.printf("- %s: %d x %.2f = %.2f%n" , item.name, item.quantity, item.price, item.quantity * item.price);

        }

        System.out.printf("Total Amount: %.2f%n", invoice.calculateTotal());
    }
}
