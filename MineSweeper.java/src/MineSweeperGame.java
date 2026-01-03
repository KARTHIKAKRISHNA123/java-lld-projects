import java.sql.SQLOutput;
import java.util.*;
public class MineSweeperGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the MineSweeper");
        System.out.println("\n Default Settings");
        System.out.println("Board Size: 8 * 8");
        System.out.println("Number of mines: 10");

        MineSweeper game = new MineSweeper(8, 8, 10);

        System.out.println("\n How To Play: ");
        System.out.println("1. Enter row and column numbers to reveal a cell");
        System.out.println("2. Numbers show how many mines are adjacent to that cell");
        System.out.println("3. '.' represents unrevealed cells");
        System.out.println("4. '*' represents mine (when game is over).");
        System.out.println("\n Let's Begin\n ");

        while (!game.isGameOver() && !game.hasWon()) {
            game.displayGame();

            System.out.println("Enter Row Number(0 - 7): ");
            int row = scanner.nextInt();

            System.out.println("Enter Col Number(0 - 7): ");
            int col = scanner.nextInt();

            boolean validMove = game.makeMove(row, col);

            if (!validMove) {
                continue;
            }

            if (game.isGameOver()) {
                System.out.println("Game Over! You hit a mine");
                game.displayGame();
            }

            else if (game.hasWon()){
                System.out.println("\n Congratulations! You have Won!");
                game.displayGame();

            }


        }

        scanner.close();




    }
}