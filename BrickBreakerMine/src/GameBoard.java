import java.util.*;

public class GameBoard  {
  private static final String WALL = "w";
  private static final String BRICK = "1";
  private static final String GROUND = "g";
  private static final String BALL = "o";
  private static final String EMPTY = " ";

  private final String[][] board;

  private final Map<Integer, Integer> brickStrengths;

  private int[] ballPosition;

  private int lives;

  public GameBoard(int rows, int cols) {
    board = new String[rows][cols];
    brickStrengths = new HashMap<>();
    lives = 5;
    initializeBoard();
    placeInitialBall();

  }

  private void initializeBoard() {
    for (int row = 0; row < board.length; row++) {
      for (int col = 0; col < board[0].length; col++) {
        if (row == 0 || col == 0 || col == board[0].length - 1) {
          board[row][col] = WALL;

        }
        else if (row == board.length - 1) {
          board[row][col] = GROUND;
        }
        else {
          board[row][col] = EMPTY;
        }
      }
    }
  }

  private void placeInitialBall() {
    int row = board.length - 1;
    int col = board[0].length / 2;
    board[row][col] = BALL;
    ballPosition = new int[] {row, col};
  }

  public void placeBrick(int row, int col, int strength) {
    board[row][col] = BRICK;
    int position = calculatePosition(row, col);
    brickStrengths.put(position, strength);
  }

  private int calculatePosition(int row, int col) {

    //For example, in a 7x7 grid:
    // Position (2,2) becomes: (2 * 7) + 2 + 1 = 17
    //Position (3,3) becomes: (3 * 7) + 3 + 1 = 25
    return (row * board[0].length ) + col + 1;

  }

  public void moveBall(int startRow, int startCol, int rowDir, int colDir) {
    moveBallInDirection(startRow,startCol, rowDir, colDir);
    if (!board[startRow][startCol].equals(BALL)) {
      board[startRow][startCol] = GROUND;

    }
  }

  private void moveBallInDirection(int row, int col, int rowDir, int colDir) {
    // 1. Keep moving until we hit a wall
    while (!board[row][col].equals(WALL)) {
      if (board[row][col].equals(BRICK)) {
        handleBrickCollision(row, col);
        return;
      }
      animateBallMovement(row, col);
      col += colDir;
      row += rowDir;
    }

    // --- LOOP BREAKS HERE (We just hit a wall) ---
    // row and col are currently at the WALL's position.

    // 2. Visualise the collision (Optional: blinks the wall)
    handleWallCollision(row, col);

    // 3. [FIX] Step back to the last valid position (Ground/Empty)
    // This prevents the ball from getting stuck in the wall's coordinates.
    row -= rowDir;
    col -= colDir;

    // 4. Update Directions
    // Reset vertical movement (physics of your game)
    rowDir = 0;
    // Reverse horizontal movement
    colDir *= -1;

    // 5. Calculate next move from the VALID position
    if (colDir == 0) {
      // If we stopped moving horizontally (e.g., Top Wall hit), drop down
      // We use row + 1 because 'row' is now the safe spot, so row+1 is the next spot down.
      moveBallDown(row + 1, col);
    }
    else {
      // Continue moving in the new direction
      moveBallInDirection(row, col + colDir, rowDir, colDir);
    }
  }

  private void handleBrickCollision(int row, int col) {
    moveBallDown(row, col);
  }

  private void handleWallCollision(int row, int col) {
    board[row][col] = BALL;
    displayBoard();
    pause();
    board[row][col] = WALL;

  }

  private void moveBallDown(int startRow, int col) {
    int row = startRow;

    while (row < board.length) {
      animateBallMovement(row, col);
      row++;
    }

    ballPosition = new int[]{row -1, col};

    board[ballPosition[0]][ballPosition[1]] = BALL;
  }

  private void animateBallMovement(int row, int col) {
    if (board[row][col].equals(BRICK)) {
      reduceBrickStrength(row, col);
      if (brickStrengths.get(calculatePosition(row, col)) == 0) {
        board[row][col] = EMPTY;
      }

    } else {
      board[row][col] = BALL;
      displayBoard();
      pause();
      board[row][col] = EMPTY;

    }
  }

  private void reduceBrickStrength(int row, int col) {
    int position = calculatePosition(row, col);
    lives--;
    if (lives >= 0) {
      brickStrengths.put(position, brickStrengths.get(position) - 1);
    }
  }

  private void pause() {
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public void displayBoard() {
    for (String[] row: board) {
      for (String cell: row) {
        System.out.print(cell + " ");
      }
      System.out.println();
    }
    System.out.println("Remaining Lives: " + lives);
  }

  public int[] getBallLocation() {
    return ballPosition;
  }

  public int getRemainingLives() {
    return lives;
  }
}
