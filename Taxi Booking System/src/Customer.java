public class Customer {
    private int customerId;
    private char pickup; //in question it is given as characters
    private char drop;
    private int pickTime;

    Customer (int customerId, char pickup, char drop, int pickTime) {
        this.customerId = customerId;
        this.pickup = pickup;
        this.drop = drop;
        this.pickTime = pickTime;
    }

    public int getCustomerId() {
        return customerId;
    }

    public char getPickup() {
        return pickup;
    }

    public char getDrop() {
        return drop;
    }

    public int getPickTime() {
        return pickTime;
    }

}
