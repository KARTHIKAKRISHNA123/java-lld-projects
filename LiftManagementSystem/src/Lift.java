public class Lift {
    private String name;
    private int currentFloor;
    private int minFloor;
    private int maxFloor;
    private int capacity;
    private boolean isUnderMaintenance;
    private Direction currentDirection;
    private int lastOperationalFloor;


    public enum Direction {
        UP, DOWN, IDLE
    }

    public Lift(String name, int minFloor, int maxFloor, int capacity) {
        this.name = name;
        this.currentFloor = 0;
        this.lastOperationalFloor = 0;
        this.minFloor = minFloor;
        this.maxFloor = maxFloor;
        this.isUnderMaintenance = false;
        this.currentDirection = Direction.IDLE;
        this.capacity = capacity;
    }

    public String getName() {
        return name;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int floor) {
        this.currentFloor = floor;
        if (!isUnderMaintenance) {
            this.lastOperationalFloor = floor;
        }
    }

    public int getMinFloor() {
        return minFloor;
    }

    public int getMaxFloor() {
        return maxFloor;
    }

    public int getCapacity() {
        return capacity;
    }

    public boolean isUnderMaintenance() {
        return isUnderMaintenance;
    }

    public void setUnderMaintenance(boolean maintenance) {
        this.isUnderMaintenance = maintenance;
        if (maintenance) {

            this.lastOperationalFloor = this.currentFloor;
            this.currentFloor = -1;

        }

        else {
            this.currentFloor = this.lastOperationalFloor;
        }
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction direction) {
        this.currentDirection = direction;
    }

    public int calculateStops(int sourceFloor, int destinationFloor) {
        return Math.abs(sourceFloor - destinationFloor);
    }

    @Override
    public String toString() {
        if (isUnderMaintenance) {
            return this.name + "M";
        }
        return name;
    }
}
