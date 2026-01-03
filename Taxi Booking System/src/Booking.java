public class Booking {
    private int bookingId;
    private int dropTime;
    private int amount;
    private Customer customer; //Aggregation storing another class object as data member

    Booking (int bookingId, int dropTime, int amount, Customer customer) {
        this.bookingId = bookingId;
        this.dropTime = dropTime;
        this.amount = amount;
        this.customer = customer;
    }

    public int getBookingId() {
        return bookingId;
    }

    public int getDropTime() {
        return dropTime;
    }

    public int getAmount() {
        return amount;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getCustomerId() {
        return this.customer.getCustomerId();
    }

    public char getPickupPoint() {
        return this.customer.getPickup();
    }

    public char getDropPoint() {
        return this.customer.getDrop();
    }

    public int getPickUpTime() {
        return this.customer.getPickTime();
    }



    // we are using getter methods so that the value can only be accessed and read but cannot be modified by the user


}
