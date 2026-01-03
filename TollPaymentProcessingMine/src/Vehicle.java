import java.util.*;
class Vehicle {
    String vehicleNumber;
    String vehicleType;
    boolean isVIP;
    List<Journey> journeys;

//    journeys = [
//            new journey("0", "2", Arrays.asList(0, 1, 2), 75),
//            new journey("2", "0", Arrays.asList(2, 1, 0), 75)
//
//            ]

    public Vehicle(String vehicleNumber, String vehicleType, boolean isVIP) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
        this.isVIP = isVIP;
        this.journeys = new ArrayList<>();
    }

    public void addJourney(Journey journey) {
        journeys.add(journey);
    }

    public void displayDetails() {
        System.out.println("Vehicle Number: " + vehicleNumber);
        System.out.println("Type: " + vehicleType + ", VIP: " + isVIP);
        for (Journey journey: journeys) {
            System.out.println("Journey: " + journey.startPoint + " -> " + journey.endPoint);
            System.out.println("Tolls Passed: " + journey.tollsPassed);
            System.out.println("Amount Paid: " + journey.amountPaid);
        }

        int totalPaid = 0;
        for (journey j: journeys) {
            totalPaid += j.amountPaid;
        }
        System.out.println("Total Amount Paid: " + totalPaid);
    }
}
