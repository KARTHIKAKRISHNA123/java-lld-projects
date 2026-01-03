import java.util.*;

public class BrickGame{
    public static void main(String[] args) {
        GameBoard gameBoard = new GameBoard(7, 7);



        gameBoard.placeBrick(2, 2, 2);
        gameBoard.placeBrick(2, 3, 2);
        gameBoard.placeBrick(2, 4, 2);
        gameBoard.placeBrick(3, 2, 2);
        gameBoard.placeBrick(3, 3, 2);
        gameBoard.placeBrick(3, 4, 2);

        Scanner scanner = new Scanner(System.in);
        while (true) {
            gameBoard.displayBoard();

            if (gameBoard.getRemainingLives() <= 0) {
                System.out.println("Game Over! No Lives Remaining.");
                System.exit(0);
            }

            System.out.println("Enter Direction (st/lt/rt): ");
            String direction = scanner.next().toLowerCase();

            int[] currentPos = gameBoard.getBallLocation();

            switch (direction) {
                case "lt" -> gameBoard.moveBall(currentPos[0], currentPos[1], -1, -1);
                case "rt" -> gameBoard.moveBall(currentPos[0], currentPos[1], -1, 1);
                case "st" -> gameBoard.moveBall(currentPos[0], currentPos[1], -1, 0);

                default -> System.out.println("Invalid Direction! Use st, lt, or rt.");
            }
        }



    }


}