import java.util.*;


public class EmployeeManagementSystem {
    private List<Employee> employees;
    private Scanner scanner;
    Map<String, List<Employee>> subordinatesMap = new HashMap<>();




    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
        scanner = new Scanner(System.in);
    }

    public void addEmployee() {
        System.out.println("\nEnter Employee Details: ");

        System.out.println("Name: ");
        String name = scanner.nextLine();

        System.out.println("Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.println("Designation: ");
        String designation = scanner.nextLine();

        System.out.println("Department: ");
        String department = scanner.nextLine();

        System.out.println("Manager Name: ");
        String managerName = scanner.nextLine();

        employees.add(new Employee(name, age, designation, department, managerName));
        System.out.println("Employee Added Successfully");

    }

    //If we add that employee then only we will be able to print that employee

    public void printAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No Employees found");
            return;
        }

        System.out.println("\n Enter The Employee Details:");
        for (Employee emp: employees) {
            System.out.println(emp);
            System.out.println("----------------------------------------------------------------");
        }
    }

    public void searchEmployee() {
        System.out.println("Enter Employee Name To Search: ");
        String searchName = scanner.nextLine();

        boolean found = false;
        for (Employee emp: employees) {
            if (emp.getName().toLowerCase().contains(searchName.toLowerCase())) {
                System.out.println("Employee Found");
                System.out.println(emp);
                found = true;
            }


        }

        if (!found) {
            System.out.println("No Employees found with the given name");
        }
    }

    public void findEmployeesUnderManager() {
        System.out.println("\nEnter manager name: ");
        String managerName = scanner.nextLine();

        System.out.println("\nEnter Department: ");
        String department = scanner.nextLine();

        boolean found = false;

        System.out.println("Employees under manager '" + managerName + "'in department '" + department+ "'" );

        for (Employee emp : employees) {
            if (emp.getManagerName().equalsIgnoreCase(managerName) && emp.getDepartment().equalsIgnoreCase(department)) {
                System.out.println(emp);
                System.out.println("----------------------------------------------------------");
                found = true;
            }



        }
        if (!found) {
            System.out.println("No Employee found under the specified manager in the specified department");
        }
    }

    public void printReportingTree() {
        subordinatesMap.clear();
        for (Employee e : employees) {
            String mgr = e.getManagerName();
            subordinatesMap.putIfAbsent(mgr, new ArrayList<>());
            subordinatesMap.get(mgr).add(e);
        }
        System.out.println("Enter the root Employee Name to Show Reporting Tree: ");
        String employeeName = scanner.nextLine();

        Employee targetEmployee = null;
        for (Employee emp: employees) {
            if (emp.getName().equalsIgnoreCase(employeeName)) {
                targetEmployee = emp;
                break;
            }
        }

        if (targetEmployee == null) {
            System.out.println("Employee is not found");
        }

        System.out.println("Reporting Tree for " + targetEmployee + "is: ");
        Set<String> visited = new HashSet<>();
        printReportingTreeRecursive(targetEmployee, 0, visited);
    }

    private void printReportingTreeRecursive(Employee employee, int level, Set<String> visited) {
        if (visited.contains(employee.getName())) {
            String indent = "  ".repeat(level);
            System.out.println(indent + "⚠️ [CYCLE DETECTED: " + employee.getName() + " reports to " + employee.getName() + "]");
            System.out.println(employee.getName() + " is a self managing employee! ");
            return;
        }

        visited.add(employee.getName());



        String indent = " ".repeat(level);
        System.out.println(indent +  "┗ " + employee.getName() + " (" + employee.getDesignation() + ")");

        List<Employee> directReports = subordinatesMap.get(employee.getName());

        if (directReports != null) {
            for (Employee subordinate : directReports) {
                printReportingTreeRecursive(subordinate, level + 1, visited);
            }
        }
        visited.remove(employee.getName());
    }

    public static void main(String[] args) {
        EmployeeManagementSystem ems = new EmployeeManagementSystem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the employee management system");
        for (int i = 0; i < 3; i++) {
            System.out.println("\nEmployee #" + (i + 1));
            ems.addEmployee();
        }

        while (true) {
            System.out.println("\n=== Employee Management System Menu ===");
            System.out.println("1. Print all employee details");
            System.out.println("2. Search employee details");
            System.out.println("3. Find employees under manager");
            System.out.println("4. Show reporting tree");
            System.out.println("5. Exit");
            System.out.print("\nEnter your choice (1-5): ");

            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    ems.printAllEmployees();
                    break;
                case 2:
                    ems.searchEmployee();
                    break;
                case 3:
                    ems.findEmployeesUnderManager();
                    break;
                case 4:
                    ems.printReportingTree();
                    break;
                case 5:
                    System.out.println("\nThank you for using Employee Management System!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }



}
