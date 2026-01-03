import java.util.*;

class Flight {
    private String flightName;
    private int availableSeats;
    private int ticketPrice;
    private Map<String, Passenger> bookings;
    private int bookingCounter;

    public Flight(String flightName) {
        this.flightName = flightName;
        this.availableSeats = 50;
        this.ticketPrice = 5000;
        this.bookings = new HashMap<>();
        this.bookingCounter = 0;
    }

    public String bookTickets(String passengerName, int age, int seats) {
        // FIX 1: Add validation for negative seats (seats > 0)
        if (seats > 0 && seats <= availableSeats) {

            // FIX 2: Calculate total cost BEFORE updating the price
            int totalBookingCost = ticketPrice * seats;

            bookingCounter++;
            String bookingId = "T" + bookingCounter;

            // FIX 3: Pass 'totalBookingCost' to the new Passenger constructor
            Passenger passenger = new Passenger(bookingId, passengerName, age, seats, totalBookingCost);

            bookings.put(bookingId, passenger);
            availableSeats -= seats;
            ticketPrice += 200 * seats;

            System.out.println("Booking successful! Total Cost: ₹" + totalBookingCost); // Optional helpful print
            return bookingId;
        } else {
            System.out.println("Booking failed: Invalid seat count or not enough seats available.");
            return null;
        }
    }

    public boolean cancelBooking(String bookingId) {
        Passenger passenger = bookings.get(bookingId);
        if (passenger != null) {
            int seats = passenger.getSeatsBooked();

            // FIX 4: Retrieve the specific amount they paid
            int refundAmount = passenger.getBookedCost();

            availableSeats += seats;
            ticketPrice -= 200 * seats;
            bookings.remove(bookingId);

            // FIX 5: Print the actual refund money
            System.out.println("Booking canceled successfully.");
            System.out.println("Refund issued: ₹" + refundAmount);
            return true;
        } else {
            System.out.println("Cancellation failed: Booking ID not found.");
            return false;
        }
    }

    public void displayDetails() {
        System.out.println("Flight: " + flightName);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("Current Ticket Price: ₹" + ticketPrice);
    }

    public void printDetails() {
        System.out.println("Flight: " + flightName);
        System.out.println("Available Seats: " + availableSeats);
        System.out.println("Current Ticket Price: ₹" + ticketPrice);
        System.out.println("Passengers:");
        for (Passenger passenger : bookings.values()) {
            System.out.println(passenger);
        }
    }
}