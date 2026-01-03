import java.net.SocketOption;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class BookingSystem {
    private List<Taxi> taxis;
    int bookingId = 1;

    BookingSystem(int taxiCount) {
        taxis = new ArrayList<>();
        for (int i = 1; i <= taxiCount; i++) {
            taxis.add(new Taxi(i));
        }
    }

    private int calculateCharges(char pickup, char drop) {
        int distance = Math.abs(pickup - drop) * 15;
        int charges = 100;
        distance -= 5;
        charges += distance * 10;
        return charges;
    }

    private Taxi findTaxi(char pickup, int pickupTime) {
        List<Taxi> freeTaxis = new ArrayList<>();

        for (Taxi t: taxis) {
            if (t.isFree(pickup, pickupTime)) {
                freeTaxis.add(t);
            }
        }

        if (freeTaxis.isEmpty()) return null;

        int minDistance = Integer.MAX_VALUE;
        for (Taxi t: freeTaxis) {
            int distance = Math.abs(pickup - t.getCurrentSpot());
            minDistance = Math.min(distance, minDistance);
        }

        List<Taxi> closest = new ArrayList<>();
        for (Taxi t: freeTaxis) {
            int distance = Math.abs(pickup - t.getCurrentSpot());
            if (distance == minDistance)
                closest.add(t);


        }

        Taxi selected = closest.get(0);
        for (Taxi t: closest) {
            if (t.getEarnings() < selected.getEarnings()) {
                selected = t;
            }
        }

        return selected;
    }

    void bookTaxi(Customer c) {
        Taxi selected = findTaxi(c.getPickup(), c.getPickTime());
        if (selected == null) {
            System.out.println("No Taxi is Available");
            return;
        }

        int travelTime = Math.abs(c.getPickup() - c.getDrop());
        int dropTime =  c.getPickTime() + travelTime;
        int charges = calculateCharges(c.getPickup(), c.getDrop());

        Booking booking = new Booking(bookingId, dropTime, charges, c);
        bookingId++;

        selected.assignBookings(booking);
        System.out.println("Taxi " + selected.getTaxiId() + " is allocated! ");

        selected.setFreeTime(dropTime);
        selected.setCurrentSpot(c.getDrop()); // Move the taxi!
        selected.addToEarnings(charges);

    }

    void displayTaxi() {
        System.out.println("-------------------------------------------------------------------------------------");
        for (Taxi t : taxis) {
            System.out.println("Taxi - " + t.getTaxiId() + " Total Earnings: " + t.getEarnings());
            System.out.println("-------------------------------------------------------------------------------------");

            // Renamed headers to be shorter (<= 6 chars) so they align with single tabs
            System.out.println("BookID\tCustID\tFrom\tTo\tPickUp\tDrop\tAmount");

            System.out.println("-------------------------------------------------------------------------------------");

            for (Booking b : t.getBookings()) {
                System.out.println(
                        b.getBookingId() + "\t" +
                                b.getCustomer().getCustomerId() + "\t" +
                                b.getCustomer().getPickup() + "\t" +
                                b.getCustomer().getDrop() + "\t" +
                                b.getCustomer().getPickTime() + "\t" +
                                b.getDropTime() + "\t" +
                                b.getAmount()
                );
            }
            System.out.println("-------------------------------------------------------------------------------------");
            System.out.println();
        }
    }



}
