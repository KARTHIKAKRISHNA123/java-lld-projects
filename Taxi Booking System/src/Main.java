//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer(1, 'A', 'B', 9);
        Customer c2 = new Customer(2, 'B', 'D', 9);
        Customer c3 = new Customer(3, 'B', 'C', 12);

        BookingSystem bookingSystem = new BookingSystem(4);


        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        bookingSystem.bookTaxi(c1);
        bookingSystem.bookTaxi(c2);
        bookingSystem.bookTaxi(c3);

        bookingSystem.displayTaxi();
        // to see how IntelliJ IDEA suggests fixing it.




    }
}