import java.util.*;
public class EventManagementSystem {

    private static Event parseEvent(String input) {
        try {
            if (input.toLowerCase().contains("lightning")) {
                String name = input.replace("lightning", "").trim();
                return new Event(name, 5);
            }

            //Database Design Fundamentals 60 mins
            // ["Database Design Fundamentals", "60 mins"]

            String[] parts = input.split("\\s+(?=\\d+\\s*mins)");
            if (parts.length == 2) {
                String name = parts[0].trim();
                //[^0 - 9] means "match any character that is not a digit"
                int duration = Integer.parseInt(parts[1].replaceAll("[^0-9]", ""));
                return  new Event(name, duration);
            }
        }
        catch (Exception e) {
            System.out.println("Invalid event format: " + input);
        }
        return null;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Event> events = new ArrayList<>();

        System.out.println("Enter events one per line. Enter done when finished.");

        while (true) {
            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("done")) {
                System.out.println();
                break;
            }

            Event event = parseEvent(input);
            if (event != null) {
                events.add(event);
            }
        }

        EventScheduler scheduler = new EventScheduler();
        scheduler.scheduleEvents(events);
        scheduler.printSchedule();

        scanner.close();
    }

}

//Enter events one per line. Enter done when finished.
//Welcome event 30 mins
//C programming 45 mins
//Working with Java Beans 30 mins
//Ruby on Rails programming 60 mins
//Introduction to Groovy 60 mins
//Rails Debugging 45 mins
//Tips and tricks in C 30 mins
//Back-end development in MySQL 50 mins
//Sit down and Take notes lightning
//Clojure Introduction 45 mins
//Team Management Concepts 30 mins
//Introduction to Java Frameworks lightning
//Working with Angular JS 45 mins
//Ruby on Rails programming web development concepts 60 mins
//Introduction to Kotlin Java 60 mins
//Debugging and Testing products 60 mins
//Documenting a software 40 mins
//Server side development 60 mins
//        done
//
//Schedule for Day 1
//        09:00: am Welcome event 30 mins
//09:30: am C programming 45 mins
//10:15: am Working with Java Beans 30 mins
//10:45: am Ruby on Rails programming 60 mins
//11:45: am Sit down and Take notes lightning
//11:50: am Introduction to Java Frameworks lightning
//4:00 PM Networking Hands On 60 mins
//
//Schedule for Day 1
//        09:00: am Introduction to Groovy 60 mins
//10:00: am Rails Debugging 45 mins
//10:45: am Tips and tricks in C 30 mins
//11:15: am Team Management Concepts 30 mins
//4:00 PM Networking Hands On 60 mins
//09:00: am Back-end development in MySQL 50 mins
//09:50: am Clojure Introduction 45 mins
//10:35: am Working with Angular JS 45 mins
//4:00 PM Networking Hands On 60 mins
//09:00: am Ruby on Rails programming web development concepts 60 mins
//10:00: am Introduction to Kotlin Java 60 mins
//11:00: am Documenting a software 40 mins
//4:00 PM Networking Hands On 60 mins
//09:00: am Debugging and Testing products 60 mins
//10:00: am Server side development 60 mins
//4:00 PM Networking Hands On 60 mins
