import java.util.*;
class Journey {
    String startPoint;
    String endPoint;
    List<Integer> tollsPassed;
    int amountPaid;

    // tollsPassed = List.of(0, 1, 2, 3);

    public Journey(String startPoint, String endPoint, List<Integer> tollsPassed, int amountPaid) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.tollsPassed = tollsPassed;
        this.amountPaid =  amountPaid;

    }
}
