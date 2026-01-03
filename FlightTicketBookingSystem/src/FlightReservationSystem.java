import java.util.*;
public class FlightReservationSystem {
    Map<String, Flight> flights;

    public FlightReservationSystem() {
        flights = new HashMap<>();
        flights.put("Indigo", new Flight("Indigo"));
        flights.put("SpiceJet", new Flight("SpiceJet"));

    }

    public String bookTicket(String flightName, String passengerName, int age, int seats) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            return flight.bookTickets(passengerName, age, seats);
        }
        else {
            System.out.println("Booking Failed: Flight Not Found!");
            return null;
        }
    }

    public boolean cancelTicket(String flightName, String bookingId) {
        Flight flight = flights.get(flightName);
        if(flight != null) {
            return flight.cancelBooking(bookingId);
        }
        else {
            System.out.println("Cancellation Failed. Flight Not Found!");
            return false;
        }
    }

    public void displayFlightDetails(String flightName) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            flight.displayDetails();
        }
        else {
            System.out.println("Flight Not found!");
        }

    }

    public void printFlightDetails(String flightName) {
        Flight flight = flights.get(flightName);
        if (flight != null) {
            flight.printDetails();
        }
        else {
            System.out.println("Flight Not Found!");
        }
    }
}
