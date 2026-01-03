import java.util.*;
class Highway {
    List<Toll> tollPoints;

//    0: Toll A - Fee: 25
//    1: Toll B - Fee: 30
//    2: Toll C - Fee: 20
//    3: Toll D - Fee: 15

    Map<String, Vehicle> vehicleRecords;

//    vehicleRecords.put("TN01AB1234", car);
//    vehicleRecords.put("TN02CD5678", bike);

    public Highway(List<Toll> tollPoints) {
        this.tollPoints = tollPoints;
        this.vehicleRecords = new HashMap<>();
    }

    public void processJourney(String vehicleNumber, String vehicleType, boolean isVIP, String start, String end, List<Integer> tollRoute) {
        Vehicle vehicle = vehicleRecords.computeIfAbsent(vehicleNumber, vn -> new Vehicle(vn, vehicleType, isVIP)); //I cant understand this
        int totalAmount = 0;
        for (int tollId: tollRoute) { //0, 1, 2
            Toll toll = tollPoints.get(tollId);// Toll{id=0, name="Toll A"}

            int charge = toll.calculateToll(vehicleType, isVIP);
            toll.recordVehicle(vehicle, charge);
            totalAmount += charge;

        }
        Journey journey = new Journey(start, end, tollRoute, totalAmount);
        vehicle.addJourney(journey);

        System.out.println("Journey Completed! Total Toll Paid: " + totalAmount);


    }

    public void displayTollDetails() {
        for (Toll toll : tollPoints) {
            toll.displayDetails();
        }
    }

    public void displayVehicleDetails() {
        for (Vehicle vehicle: vehicleRecords.values()) {
            vehicle.displayDetails();
        }

    }

    public List<Integer> findCircularRoute (int start, int end) {
        List<Integer> forwardRoute = new ArrayList<>();
        List<Integer> backwardRoute = new ArrayList<>();
        int totalTolls = tollPoints.size();

        for (int i = 0; i != end; i = (i + 1) % totalTolls) {
            forwardRoute.add(i);
        }
        forwardRoute.add(end);

        for (int i = start; i != end; i = (i - 1 + totalTolls) % totalTolls) {
            backwardRoute.add(i);
        }
        backwardRoute.add(end);

        return forwardRoute.size() < backwardRoute.size() ? forwardRoute: backwardRoute;
    }

    public int calculateRegularTollForRoute(int start, int end, String vehicleType, boolean isVIP) {
        int totalCost = 0;
        if (start <= end) {
            for (int i = 0; i <= end; i++) {
                Toll toll = tollPoints.get(i);
                totalCost += toll.calculateToll(vehicleType, isVIP);

            }
        }
        else {
            //[0, 1, 2]
            for (int i = start; i < tollPoints.size(); i++) {
                Toll toll = tollPoints.get(i);
                totalCost += toll.calculateToll(vehicleType, isVIP);
            }
            for (int i = 0; i <= end; i++) {
                Toll toll = tollPoints.get(i);
                totalCost += toll.calculateToll(vehicleType, isVIP);
            }
        }
        return totalCost;
    }

    public int calculateTollForRoute(List<Integer> tollRoute, String vehicleType, boolean isVIP) {
        int totalCost = 0;
        for (int tollId : tollRoute) {
            Toll toll = tollPoints.get(tollId);
            totalCost += toll.calculateToll(vehicleType, isVIP);
        }
        return totalCost;
    }

}
