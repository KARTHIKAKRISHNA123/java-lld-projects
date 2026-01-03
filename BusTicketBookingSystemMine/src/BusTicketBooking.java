import java.util.*;

public class BusTicketBooking {

    private List<Customer> customers;
    private List<Bus> buses;
    private List<Ticket> tickets;
    private int customerIdCounter;
    private int busIdCounter;
    private int ticketIdCounter;
    private Customer currentCustomer;

    public BusTicketBooking() {
        customers = new ArrayList<>();
        buses = new ArrayList<>();
        tickets = new ArrayList<>();
        customerIdCounter = 1;
        busIdCounter = 1;
        ticketIdCounter = 1;
        currentCustomer = null;
        initializeBuses();
    }

    private void initializeBuses() {
        buses.add(new Bus(busIdCounter++, "AC", "Seater", 30));
        buses.add(new Bus(busIdCounter++, "NON - AC", "Seater", 40));
        buses.add(new Bus(busIdCounter++, "AC", "Sleeper", 20));
        buses.add(new Bus(busIdCounter++, "NON - AC", "Sleeper", 20));

    }

    public void signUp(Scanner scanner) {
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        System.out.println("Enter Your Password: ");
        String password = scanner.nextLine();
        System.out.println("Enter Your Age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Your Gender: ");
        String gender = scanner.nextLine();

        Customer customer = new Customer(customerIdCounter++, name, password, age, gender);
        customers.add(customer);
        System.out.println("Sign Up Successful! Your customer Id is: " + customer.getId());

    }

    public boolean login(Scanner scanner) {
        System.out.println("Enter Your Customer ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Enter Your Password: ");
        String password = scanner.nextLine();


        for (Customer customer: customers) {
            if (customer.getId() == id && customer.getPassword().equals(password)) {
                currentCustomer = customer;
                System.out.println("Login Successful! Welcome, " + customer.getName());
                return true;
            }

        }

        System.out.println("Invalid Credentials!");
        return false;

    }

    public void showAvailableBuses() {
        System.out.println("The Available Buses are: ");
        for (Bus bus: buses) {
            System.out.println(bus);

        }
    }

    public void bookTicket(Scanner scanner) {
        if (currentCustomer == null) {
            System.out.println("Please Login First!");
            return;
        }

        showAvailableBuses();
        System.out.println("Enter Bus ID: ");
        int busId = scanner.nextInt();
        scanner.nextLine();

        Bus selectedBus = null;

        for (Bus bus : buses) {
            if (bus.getId() == busId) {
                selectedBus = bus;
                break;
            }
        }

        if (selectedBus == null) {
            System.out.println("Invalid Bus Id!");
            return;
        }

        selectedBus.showAvailableSeats();
        System.out.println("Enter Number of Tickets to book: ");
        int numberOfTickets = scanner.nextInt();
        scanner.nextLine();

        if (numberOfTickets > selectedBus.getAvailableSeats()) {
            System.out.println("Not Enough Seats Available!");
            return;
        }

        // --- LOGIC FIX START: COLLECT AND VALIDATE ALL INPUTS FIRST ---
        List<Integer> seatsToBook = new ArrayList<>();

        // Phase 1: Input Collection & Validation
        for (int i = 0; i < numberOfTickets; i++) {
            System.out.println("Enter seat number for Ticket " + (i + 1) + ": ");
            int seatNumber = scanner.nextInt() - 1; // Convert 1-based index to 0-based
            scanner.nextLine();

            // Check 1: Boundary Check (Fixed the >= error)
            if (seatNumber < 0 || seatNumber >= selectedBus.getTotalSeats()) {
                System.out.println("Invalid Seat Number: " + (seatNumber + 1));
                return;
            }

            // Check 2: Duplicate Entry (User entering same seat twice)
            if (seatsToBook.contains(seatNumber)) {
                System.out.println("Duplicate seat number entered. Booking cancelled.");
                return;
            }

            // Check 3: Availability Check (Using the new helper method)
            if (selectedBus.isSeatBooked(seatNumber)) {
                System.out.println("Seat " + (seatNumber + 1) + " is already booked. Transaction cancelled.");
                return;
            }

            seatsToBook.add(seatNumber);
        }

        // Phase 2: Commitment (Actually book the seats)
        // We only reach here if ALL checks passed, so this is safe.
        for (int seat : seatsToBook) {
            selectedBus.bookSeat(seat);
        }
        // --- LOGIC FIX END ---

        double baseFare = selectedBus.getBusType().equals("AC") ? 1000 : 500;
        baseFare += selectedBus.getSeatType().equals("Sleeper") ? 500 : 0;

        double totalFare = baseFare * numberOfTickets;

        Ticket ticket = new Ticket(ticketIdCounter++, selectedBus, numberOfTickets, totalFare, currentCustomer.getId());

        for (int seat : seatsToBook) {
            ticket.addBookedSeats(seat);
        }
        tickets.add(ticket);

        System.out.println("\n Booking Successful! ");
        System.out.println("Ticket ID: " + ticket.getId());
        System.out.println("Total Fare: " + totalFare);
        System.out.println("Booked Seats: " + seatsToBook);
    }

    public void viewTickets() {
        if (currentCustomer == null) {
            System.out.println("Please Login First!");
            return;
        }

        System.out.println("\n Your tickets: ");
        boolean found = false;
        for (Ticket ticket: tickets) {
            if (ticket.getCustomerId() == currentCustomer.getId()) {
                System.out.println(ticket);
                found = true;
            }

        }

        if (!found) {
            System.out.println("No Tickets Found!");
        }

    }

    public void cancelTicket(Scanner scanner) {
        if (currentCustomer == null) {
            System.out.println("Please Login First!");
            return;
        }

        viewTickets();
        System.out.println("\n Enter Ticket ID to cancel: ");
        int ticketID = scanner.nextInt();
        scanner.nextLine();

        Ticket ticketToCancel = null;
        for (Ticket ticket: tickets) {
            if (ticket.getId() == ticketID && ticket.getCustomerId() == currentCustomer.getId()) {
                ticketToCancel = ticket;
                break;
            }
        }

        if (ticketToCancel == null) {
            System.out.println("Invalid Ticket ID!");
            return;
        }

        Bus bus = ticketToCancel.getBus();
        for (int seat: ticketToCancel.getBookedSeats()) {
            bus.cancelSeat(seat);
        }

        tickets.remove(ticketToCancel);
        System.out.println("Ticket Cancelled Successfully!");
        System.out.println("Refund Amount: " + ticketToCancel.getFare());
    }

    public void showBusSummary() {
        System.out.println("\n Bus Summary: ");
        for (Bus bus: buses) {
            System.out.println("Bus ID: " + bus.getId());
            System.out.println("Type: " + bus.getBusType());
            System.out.println("Seat Type: " + bus.getSeatType());
            System.out.println("Total Seats: " + bus.getTotalSeats());
            System.out.println("Booked Seats: " + (bus.getTotalSeats() - bus.getAvailableSeats()));
            System.out.println("Available Seats: " + bus.getAvailableSeats());
            System.out.println();
        }
    }

    public static void main(String[] args) {
        BusTicketBooking system = new BusTicketBooking();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n Bus Ticket Booking System: ");
            System.out.println("1. Sign UP");
            System.out.println("2. Login");
            System.out.println("3. Show Available Buses");
            System.out.println("4. Book Ticket");
            System.out.println("5. View Tickets");
            System.out.println("6. Cancel Ticket");
            System.out.println("7. Show Bus Summary");
            System.out.println("8. Exit");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    system.signUp(scanner);
                    break;
                case 2:
                    system.login(scanner);
                    break;
                case 3:
                    system.showAvailableBuses();
                    break;
                case 4:
                    system.bookTicket(scanner);
                    break;
                case 5:
                    system.viewTickets();
                    break;
                case 6:
                    system.cancelTicket(scanner);
                    break;
                case 7:
                    system.showBusSummary();
                    break;
                case 8:
                    System.out.println("Thank you for using Bus Ticket Booking System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
