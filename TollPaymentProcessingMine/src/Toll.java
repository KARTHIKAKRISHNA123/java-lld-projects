import java.util.*;
class Toll {

    int tollId;
    Map<String, Integer> chargesPerVehicleType;// chargesPerVehicleType.put("car", 50);
    List<VehiclePayment> vehiclesPassed;
    int totalRevenue;

    public Toll(int tollId, Map<String, Integer> chargesPerVehicleType) {
        this.tollId = tollId;
        this.chargesPerVehicleType = chargesPerVehicleType;
        this.vehiclesPassed = new ArrayList<>();
        this.totalRevenue = 0;

    }

    public int calculateToll(String vehicleType, boolean isVIP) {
        int charge = chargesPerVehicleType.getOrDefault(vehicleType, 0);
        if (isVIP) {
            charge = charge - (charge / 5);
        }
        return charge;
    }

    public void recordVehicle(Vehicle vehicle, int charge) {
        vehiclesPassed.add(new VehiclePayment(vehicle.vehicleNumber, charge));
        totalRevenue += charge;
    }

    public void displayDetails() {
        System.out.println("Toll Id: " + tollId);
        System.out.println("Vehicles Passed: ");
        for (VehiclePayment vp: vehiclesPassed) {
            System.out.println("Vehicle: " + vp.vehicleNumber + ", Paid: " + vp.amountPaid);
        }
        System.out.println("Total Revenue: " + totalRevenue);
    }

//    vehiclesPassed = [
//            new VehiclePayment("1234", 50),
//            new VehiclePayment("5678", 25),
//            new VehiclePayment("9101", 100)
//
//            ];




}