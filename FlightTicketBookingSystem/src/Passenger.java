class Passenger {
    private String bookingId;
    private String name;
    private int age;
    private int seatsBooked;
    private int bookedCost; // <--- NEW FIELD

    // Updated Constructor to accept 'bookedCost'
    public Passenger(String bookingId, String name, int age, int seatsBooked, int bookedCost) {
        this.bookingId = bookingId;
        this.name = name;
        this.age = age;
        this.seatsBooked = seatsBooked;
        this.bookedCost = bookedCost; // <--- ASSIGNMENT
    }

    public String getBookingId() {
        return bookingId;
    }

    public int getSeatsBooked() {
        return seatsBooked;
    }

    // <--- NEW GETTER for Refund Logic
    public int getBookedCost() {
        return bookedCost;
    }

    @Override
    public String toString() {
        // Included cost in print details
        return "Passenger{bookingId='" + bookingId + "', name='" + name + "', age=" + age +
                ", seatsBooked=" + seatsBooked + ", costPaid=₹" + bookedCost + "}";
    }
}