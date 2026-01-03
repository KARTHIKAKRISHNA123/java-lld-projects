public class Bus {
    private int id;
    private String busType;// AC/Non-AC
    private String seatType; // Seater/Sleeper
    private int totalSeats;
    private boolean[] seats;

    public Bus(int id, String busType, String seatType, int totalSeats) {
        this.id = id;
        this.busType = busType;
        this.seatType = seatType;
        this.totalSeats = totalSeats;
        this.seats = new boolean[totalSeats];
    }

    public int getId() {
        return id;
    }

    public String getSeatType() {
        return seatType;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public int getAvailableSeats() {
        int count = 0;
        for (boolean seat : seats) {
            if (!seat) count++;

        }
        return count;
    }

    public String getBusType() {
        return busType;
    }

    // Add this inside Bus.java
    public boolean isSeatBooked(int seatNumber) {
        return seats[seatNumber];
    }

    public boolean bookSeat(int seatNumber) {
        if (seatNumber < 0 || seatNumber >= totalSeats) {
            return false;
        }

        if (!seats[seatNumber]) {
            seats[seatNumber] = true;
            return true;
        }

        return false;
    }

    public boolean cancelSeat(int seatNumber) {
        if (seatNumber < 0 || seatNumber >= totalSeats) {
            return false;
        }

        if (seats[seatNumber]) {
            seats[seatNumber] = false;
            return true;
        }

        return false;
    }

    public void showAvailableSeats() {
        System.out.println("Available Seats for Bus " + id + ":");
        for (int i = 0; i < totalSeats; i++) {
            if (!seats[i]) { // false means seat is available, seat is not booked yet
                System.out.println((i + 1) + " ");
            }
        }
        System.out.println();
    }

    @Override
    public String toString() {
        return "Bus {" +
                "id =" + id +
                ", busType = '" + busType + '\'' +
                ", seatType = '" + seatType + '\'' +
                ", totalSeats = " + totalSeats +
                ", availableSeats = " + getAvailableSeats() +
                '}';
    }
}