import java.util.*;

public class MineSweeper {
    private int[][] board;
    private char[][] displayBoard;
    private int rows;
    private int cols;
    private int numMines; //10
    private boolean gameOver; // false
    private int remainingCells;

    public MineSweeper(int rows, int cols, int numMines) {
        this.rows = rows;
        this.cols = cols;
        this.numMines = numMines;
        this.board = new int[rows][cols];
        this.displayBoard = new char[rows][cols];
        this.gameOver = false;
        this.remainingCells = rows * cols - numMines; // 64 - 54 = 10
        initializeBoards();
        placeMines();
        calculateNumbers();

    }

    private void initializeBoards() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                board[i][j] = 0;
                displayBoard[i][j] = '.';

            }
        }
    }

    private void placeMines() {
        Random random = new Random();
        int minesPlaced = 0;

        while (minesPlaced < numMines) { // 0 < 10
            int row = random.nextInt(rows);
            int col = random.nextInt(cols);

            if (board[row][col] != -1) {
                board[row][col] = -1; // -1 represents mines
                minesPlaced++; // 0 -> 1
            }
        }
    }

    private void calculateNumbers() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] != -1) {
                    board[i][j] = countAdjacentMines(i, j);
                }
            }
        }
    }

    private int countAdjacentMines(int row, int col) {
        int count = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int newRow = row + i;
                int newCol = col + j;
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols) {
                    if (board[newRow][newCol] == -1) {
                        count++;
                    }
                }

            }
        }
        return count;
    }

    public void displayGame() {
        System.out.print("  ");
        for (int j = 0; j < cols; j++) {
            System.out.print(j + " ");

        }
        System.out.println();

        for (int i = 0; i < rows; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < cols; j++) {
                System.out.print(displayBoard[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();

    }

    public boolean makeMove(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            System.out.println("Invalid Move! Please Try again");
            return false;
        }

        if (displayBoard[row][col] != '.') {
            System.out.println("Cell Already revealed! Try another one.");
            return  false;

        }

        if (board[row][col] == -1) {
            revealAllMines();
            gameOver = true;
            return true;
        }

        revealCell(row, col); // 0 reveals itself as well as its adjacent 8 cells has no bombs
         return  true;
    }

    private void revealCell(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols || displayBoard[row][col] != '.') {
            return;
        }

        remainingCells--;
        if (board[row][col] == 0) {
            displayBoard[row][col] = '0';
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    revealCell(row + i, col + j);
                }
            }
        }
        else {
            displayBoard[row][col] = (char) (board[row][col] + '0');
        }
    }

    private void revealAllMines() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == -1) {
                    displayBoard[i][j] = '*';
                }
            }
        }
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public boolean hasWon() {
        return  remainingCells == 0;
    }
}
