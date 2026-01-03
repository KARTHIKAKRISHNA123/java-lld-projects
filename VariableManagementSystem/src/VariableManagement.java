import java.util.*;

public class VariableManagement {
    private static class TransactionState {
        Map<String, Integer> variables;
        Map<Integer, Integer> valueCount;

        TransactionState() {
            variables = new HashMap<>();
            valueCount = new HashMap<>();
        }

        TransactionState(Map<String, Integer> vars, Map<Integer, Integer> counts) {
            variables = new HashMap<>(vars);
            valueCount = new HashMap<>(counts);
        }
    }

    private Stack<TransactionState> transactions;

    private Map<String, Integer> variables;
    private Map<Integer, Integer> valueCount;

    public VariableManagement() {
        transactions = new Stack<>();
        variables = new HashMap<>();
        valueCount = new HashMap<>();

    }

    public void set(String var, int value) {
        if (variables.containsKey(var)) {
            int oldValue = variables.get(var);
            valueCount.put(oldValue, valueCount.get(oldValue) - 1);
            if (valueCount.get(oldValue) == 0) {
                valueCount.remove(oldValue);
            }
        }

        variables.put(var, value);
        valueCount.put(value, valueCount.getOrDefault(value, 0) + 1);
    }

    public Integer get(String var) {
        return variables.getOrDefault(var, null);
    }

    public void unset(String var) {
        if (variables.containsKey(var)) {
            int value = variables.get(var);
            valueCount.put(value, valueCount.get(value) - 1);
            if (valueCount.get(value) == 0) {
                valueCount.remove(value);
            }

            variables.remove(var);
        }
    }

    public Integer count(int value) {
        return valueCount.getOrDefault(value, 0);
    }

    public void begin() {
        transactions.push(new TransactionState(variables, valueCount));
    }

    public boolean rollback() {
        if (transactions.isEmpty()) return false;

        TransactionState state = transactions.pop();

        variables = state.variables;
        valueCount = state.valueCount;

        return true;
    }

    public boolean commit() {
        if (transactions.isEmpty()) return false;
        transactions.clear();
        return true;
    }

    public static void main(String[] args) {
        VariableManagement system = new VariableManagement();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter commands (type EXIT to quit): ");

        while (true) {
            String line = scanner.nextLine().trim();
            if (line.equalsIgnoreCase("Exit")) break;

            String[] parts = line.split("\\s+"); //["set", "x", "10" ]
            String command = parts[0].toUpperCase(); //"SET"

            try {
                switch (command) {
                    case "SET":
                        if (parts.length != 3) {
                            System.out.println("Invalid SET command. Usage: SET variable value");
                            continue;
                        }
                        system.set(parts[1], Integer.parseInt(parts[2]));
                        break;

                    case "GET":
                        if (parts.length != 2) {
                            System.out.println("Invalid GET command. Usage: GET variable");
                            continue;
                        }

                        Integer value = system.get(parts[2]);
                        System.out.println(value != null ? value: "null");
                        break;

                    case "UNSET":
                        if (parts.length != 2) {
                            System.out.println("Invalid UNSET command. Usage: GET variable");
                            continue;
                        }
                        system.unset(parts[1]);
                        break;

                    case "COUNT":
                        if (parts.length != 2) {
                            System.out.println("Invalid COUNT command. Usage: COUNT variable");
                            continue;
                        }
                        System.out.println(system.count(Integer.parseInt(parts[1])));
                        break;

                    case "BEGIN":
                        system.begin();
                        break;


                    case "ROLLBACK":
                        if (!system.rollback()) {
                            System.out.println("No Transaction");

                        }

                        break;

                    case "COMMIT":
                        if (!system.commit()) {
                            System.out.println("No Transaction");
                        }
                        break;

                    default:
                        System.out.println("Unknown command: "+ command);


                }
            }
            catch (NumberFormatException e) {
                System.out.println("Invalid Number Format: ");
            }
            catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
        scanner.close();

    }
}