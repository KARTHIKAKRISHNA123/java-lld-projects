import java.util.*;
public class LiftManagementSystem {

    private List<Lift> lifts;

    public LiftManagementSystem() {
        lifts = new ArrayList<>();
        lifts.add(new Lift("L1", 0, 5, 4));
        lifts.add(new Lift("L2", 0, 5, 6));
        lifts.add(new Lift("L3", 6, 10, 8));
        lifts.add(new Lift("L4", 6, 10, 10));
        lifts.add(new Lift("L5", 0, 10, 12));




    }

    // IN LiftManagementSystem.java

    public void displayListPosition() {
        System.out.print("Lift: ");
        for (Lift lift : lifts) {
            System.out.printf("%-4s", lift.getName()); // Formatting for neatness
        }
        System.out.println();

        System.out.print("Floor: ");
        for (Lift lift : lifts) {
            if (lift.isUnderMaintenance()) {
                System.out.printf("%-4s", "M"); // Fix the "-1" display bug
            } else {
                System.out.printf("%-4d", lift.getCurrentFloor());
            }
        }
        System.out.println();
    }

    // CHANGE 1: Return type is 'Lift', not 'void'
    public Lift assignLift(int sourceFloor, int destinationFloor, int numPeople) {

        // CHANGE 2: No println here. Just return null if invalid.
        if (!isValid(sourceFloor) || !isValid(destinationFloor)) {
            return null;
        }

        Lift bestLift = findBestLift(sourceFloor, destinationFloor, numPeople);

        if (bestLift != null) {
            // 1. Capture distance (Optional logic for records)
            int distance = Math.abs(bestLift.getCurrentFloor() - sourceFloor);

            // 2. Move the lift
            bestLift.setCurrentFloor(destinationFloor);

            // 3. RETURN the object so Main can print the details
            return bestLift;
        }

        // CHANGE 3: If no lift found, return null explicitly.
        return null;
    }

    private Lift findBestLift(int sourceFloor, int destinationFloor, int numPeople) {
        Lift.Direction requestDirection = sourceFloor < destinationFloor ? Lift.Direction.UP : Lift.Direction.DOWN;
        Lift nearestLift = null;
        int minDistance = Integer.MAX_VALUE;
        int minStops = Integer.MAX_VALUE;

        for (Lift lift: lifts) {
            if (lift.isUnderMaintenance()) continue;
            if (numPeople > lift.getCapacity()) continue;

            if(!isLiftInValidRange(lift, sourceFloor, destinationFloor)) continue;

            int distance = Math.abs(lift.getCurrentFloor() - sourceFloor);

            int stops = lift.calculateStops(sourceFloor, destinationFloor);

            if (distance < minDistance) {
                minDistance = distance;
                minStops = stops;
                nearestLift = lift;
            }
            else if (distance == minDistance) {
                if (lift.getCurrentDirection() == requestDirection && nearestLift.getCurrentDirection() != requestDirection) {
                    // Prefer lift in same direction
                    nearestLift = lift;
                }
                else if (stops < minStops) {
                    //If direction same/different, prefer fewer stops
                    minStops = stops;
                    nearestLift = lift;
                }
            }
        }
        if (nearestLift != null) {
            nearestLift.setCurrentDirection(requestDirection);
            // Set the direction of the nearest lift to the request direction
        }

        return  nearestLift;
    }

    private boolean isValid(int floor) {
        return floor >= 0 && floor <= 10;
    }

    private boolean isLiftInValidRange(Lift lift, int sourceFloor, int destinationFloor) {
        return sourceFloor >= lift.getMinFloor() && sourceFloor <= lift.getMaxFloor() && destinationFloor >= lift.getMinFloor() && destinationFloor <= lift.getMaxFloor();
    }

    public void setLiftMaintenance(String liftName, boolean maintenance) {
        for (Lift lift : lifts) {
            if (lift.getName().equalsIgnoreCase(liftName)) {
                lift.setUnderMaintenance(maintenance);
                if (maintenance) {
                    System.out.println(liftName + " is now under maintenance!");
                }
                else {
                    System.out.println(liftName + " is now operational!");
                }
                return;
            }
        }
        System.out.println("Invalid Lift Name! ");
    }
}
