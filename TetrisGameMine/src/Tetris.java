import java.util.*;
public class Tetris {
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 10;
    private static final char EMPTY_CELL = '.';
    private static final char FILLED_CELL = '#';

    private char[][] board;
    private int currentScore;
    private boolean gameOver;
    private TetrisPiece currentPiece;


    private int currentX;
    private int currentY;

    public Tetris() {
        board = new char[BOARD_HEIGHT][BOARD_WIDTH];
        initializeBoard();
        currentScore = 0;
        gameOver = false;

    }

    // Tetris constructor will start the game I guess

    private void initializeBoard() {
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            for (int j = 0; j < BOARD_WIDTH; j++) {
                board[i][j] = EMPTY_CELL;
            }
        }
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        spawnNewPiece();

        while(!gameOver) {
            displayBoard();
            System.out.println("Score: " + currentScore);
            System.out.println("Commands: a (left), d (right), s (down), w (rotate) ");
            System.out.print("Enter Command: ");

            String command = scanner.nextLine().toLowerCase();

            switch(command) {
                case "a":
                    moveLeft();
                    break;

                case "d":
                    moveRight();
                    break;

                case "s":
                    if(!moveDown()) {
                        placePiece();
                        clearLines();
                        spawnNewPiece();
                    }
                    break;
                case "w":
                    rotate();
                    break;
                case "q":
                    gameOver = true;
                    break;
            }
        }

        System.out.println("Game Over! Final Score:" + currentScore);
        scanner.close();
    }

    private void displayBoard() {
        char[][] tempBoard = new char[BOARD_HEIGHT][BOARD_WIDTH];

        for (int i = 0; i < BOARD_HEIGHT; i++) {
            tempBoard[i] = board[i].clone(); // can we use Arrays.copyOf();
        }

        if (currentPiece != null) {
            char[][] shape = currentPiece.getShape();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[0].length; j++) {
                    if (shape[i][j] == FILLED_CELL) {
                        int y = currentY + i;
                        int x = currentX + j;
                        if (y >= 0 && y < BOARD_HEIGHT && x >= 0 && x < BOARD_WIDTH) {
                            tempBoard[y][x] = FILLED_CELL;
                        }
                    }
                }
            }
        }

        System.out.println("\n".repeat(50));
        System.out.println("-".repeat(BOARD_WIDTH + 2));
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            System.out.print("|");
            for (int j = 0; j < BOARD_WIDTH; j++) {
                System.out.print(tempBoard[i][j]);


            }
            System.out.print("|");
            System.out.println();
        }
        System.out.println("-".repeat(BOARD_WIDTH + 2));
    }

    private void spawnNewPiece() {

        currentPiece = new TetrisPiece();

        currentX = BOARD_WIDTH / 2 - currentPiece.getWidth() / 2;
        currentY = 0;

        if (!isValidMove(currentX, currentY, currentPiece.getShape())) {
            gameOver = true;
        }
    }

    private boolean isValidMove(int newX, int newY, char[][] shape) {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[0].length; j++) {
                if (shape[i][j] == FILLED_CELL) {
                    int x = newX + j;
                    int y = newY + i;

                    if (x < 0 || x >= BOARD_WIDTH || y >= BOARD_HEIGHT) // y there is no check for y < 0
                        return  false;

                    //collision detection with preexisting pieces
                    if (y >= 0 && board[y][x] == FILLED_CELL) {
                        return false;
                    }
                }
            }
        }
        return  true;


    }

    private void moveLeft() {
        if (isValidMove(currentX - 1, currentY, currentPiece.getShape())) currentX--;
    }

    private void moveRight() {
        if (isValidMove(currentX + 1, currentY, currentPiece.getShape())) currentX++;
    }

    private boolean moveDown() {
        if (isValidMove(currentX, currentY + 1, currentPiece.getShape())) {
            currentY++;
            return true;
        }
        return false;


    }

    private void rotate() {
        char[][] rotatedShape = currentPiece.getRotatedShape();
        if(isValidMove(currentX, currentY, rotatedShape) ) {
            currentPiece.rotate();
        }
    }

    private void placePiece() {
        char[][] shape = currentPiece.getShape();
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if(shape[i][j] == FILLED_CELL) {
                    int y = currentY + i;
                    int x = currentX + j;
                    if (y >= 0 && y < BOARD_HEIGHT && x >= 0 && x < BOARD_WIDTH) {
                        board[y][x] = FILLED_CELL;
                    }
                }
            }
        }
    }

    private void clearLines() {
        int linesCleared = 0;

        for (int i = BOARD_HEIGHT - 1; i >= 0; i--) {
            boolean isLineFull = true;
            for (int j = 0; j < BOARD_WIDTH; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    isLineFull = false;
                    break;
                }
            }

            if (isLineFull) {
                linesCleared++;

                for (int k = i; k > 0; k--) {
                    board[k] = board[k - 1].clone();


                }

                Arrays.fill(board[0], EMPTY_CELL);
                i++;
            }
        }

        if (linesCleared > 0) {
            currentScore += Math.pow(2, linesCleared - 1) * 100;
        }
    }

    public static void main(String[] args) {
        Tetris game = new Tetris();
        game.start();
    }
}


