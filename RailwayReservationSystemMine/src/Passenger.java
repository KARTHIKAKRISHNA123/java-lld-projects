public class Passenger {
    String name;
    int age;
    String gender;
    String berthPreference; // lower, upper or middle berth
    String allotedBirth;
    String ticketId; // Important for cancelling tickets

    public Passenger(String name, int age, String gender, String berthPreference, String allotedBirth, String ticketId) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.berthPreference = berthPreference;
        this.allotedBirth = allotedBirth;
        this.ticketId = ticketId;
    }

    @Override
    public String toString() {
        return "Ticket ID: " + ticketId + ", Name: " + name + ", Age: " + age + ", Gender: " + gender + ", Berth: " + allotedBirth;
    }
}