import java.util.*;

public class Ticket {
    private int id;
    private List<Integer> bookedSeats;
    private Bus bus;
    private int numberOfTickets;
    private double fare;
    private int customerId;


    public Ticket(int id, Bus bus, int numberOfTickets, double fare, int customerId) {
        this.id = id;
        this.bus = bus;
        this.numberOfTickets = numberOfTickets;
        this.fare = fare;
        this.customerId = customerId;
        this.bookedSeats = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public List<Integer> getBookedSeats() {
        return bookedSeats;
    }

    public Bus getBus() {
        return bus;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public double getFare() {
        return fare;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void addBookedSeats(int seatNumber) {
        bookedSeats.add(seatNumber);
    }

    @Override
    public String toString() {
        return "Ticket {" +
                "id = " + id +
                ", bookedSeats = " + bookedSeats +
                ", bus = " + bus +
                ", numberOfTickets = " + numberOfTickets +
                ", fare = " + fare +
                ", customerId = " + customerId +
                " }";
    }
}
