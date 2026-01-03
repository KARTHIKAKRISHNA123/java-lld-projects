import java.util.*;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        LiftManagementSystem liftSystem = new LiftManagementSystem();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nLift Management System");
            System.out.println("1. Display Lift Positions");
            System.out.println("2. Request Lift");
            System.out.println("3. Mark Lift For Maintenance");
            System.out.println("4. Make Lift Operational");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();

            switch(choice) {
                case 1:
                    liftSystem.displayListPosition();
                    break;

                // IN Main.java

                case 2:
                    System.out.print("Enter Source Floor: ");
                    int sourceFloor = scanner.nextInt();
                    System.out.print("Enter Destination Floor: ");
                    int destinationFloor = scanner.nextInt();
                    System.out.print("Enter Number Of People: ");
                    int numPeople = scanner.nextInt();

                    // CALL THE CONTROLLER
                    Lift assignedLift = liftSystem.assignLift(sourceFloor, destinationFloor, numPeople);
k

                    // HANDLE THE UI OUTPUT HERE
                    if (assignedLift != null) {
                        System.out.println("---------------------------------------------");
                        System.out.println("LIFT ASSIGNED: " + assignedLift.getName());
                        System.out.println("Current Position: " + assignedLift.getCurrentFloor());
                        System.out.println("Reason: Nearest available lift.");
                        System.out.println("---------------------------------------------");

                        // Optional: Show status after assignment
                        liftSystem.displayListPosition();
                    } else {
                        System.out.println("---------------------------------------------");
                        System.out.println("NO LIFT ASSIGNED");
                        System.out.println("Reason: Capacity full, Maintenance, or Range restrictions.");
                        System.out.println("---------------------------------------------");
                    }
                    break;

                case 3:
                    System.out.print("Enter lift name(L1, L2, L3, L4, L5): ");
                    String liftName = scanner.next().toUpperCase();
                    liftSystem.setLiftMaintenance(liftName, true);
                    break;

                case 4:
                    System.out.print("Enter lift name(L1, L2, L3, L4, L5): ");
                    String liftName2 = scanner.next().toUpperCase();
                    liftSystem.setLiftMaintenance(liftName2, false);
                    break;

                case 5:
                    System.out.println("Thank You For Using Lift Management System!");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid Choice! Please Try Again.");
            }





        }

    }
}