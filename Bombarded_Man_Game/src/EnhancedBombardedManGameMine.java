import java.util.*;
class EnhancedBombardedManGameMine {
    private static final int GRID_SIZE = 10;
    private static final char PLAYER = 'P';
    private static final char BOMB = 'B';
    private static final char EMPTY = '.';
    private static final char POWERUP = '*';
    private static final char EXPLOSION = 'X';

    private char[][] grid;
    private int playerRow;
    private int playerCol;
    private int score;
    private int level;
    private int lives;
    private boolean gameOver;
    private Random random;
    private Scanner scanner;
    private List<Bomb> bombs;
    private List<Explosion> explosions;

    private class Bomb {
        int row;
        int col;
        int timer;

        public Bomb(int row, int col) {
            this.row = row;
            this.col = col;
            this.timer = 5; //Our Bomb will explode after 5 seconds
        }
    }

    private class Explosion {
        int row;
        int col;
        int duration;

        public Explosion(int row, int col) {
            this.row = row;
            this.col = col;
            this.duration = 2; //Explosions will last for 2 moves
        }
    }

    public EnhancedBombardedManGameMine() {
        grid = new char[GRID_SIZE][GRID_SIZE];
        random = new Random();
        scanner = new Scanner(System.in);
        score = 0;
        level = 1;
        lives = 3;
        gameOver = false;
        bombs = new ArrayList<>();
        explosions = new ArrayList<>();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = EMPTY;
            }
        }

        playerRow = GRID_SIZE / 2;
        playerCol = GRID_SIZE / 2;
        grid[playerRow][playerCol] = PLAYER;

        spawnBombs();
        spawnPowerup();
    }

    public void play() {
        System.out.println("Welcome to Enhanced Bombarded Man Game created by KK!");
        System.out.println("Use W(up), A(left), S(down), D(right) to move.");
        System.out.println("Avoid Bombs(B) and Explosions(X)!");
        System.out.println("Collect powerups (*) to gain extra lives");
        System.out.println("Press Q to quit");

        displayGrid();

        while (!gameOver) {
            System.out.print("Enter Your Move: ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("Q")) {
                System.out.println("Game Ended By the Player. Final Score: " + score);
                break;
            }

            if (input.length() > 0) {
                char move = input.charAt(0);
                if (processMove(move)) {
                    updateBombs();
                    updateExplosions();

                    if (score % 5 == 0) {
                        spawnBombs();
                    }

                    if (score % 10 == 0) {
                        spawnPowerup();
                    }

                    if (score % 20 == 0 && score > 0) {
                        levelUp();
                    }

                    checkCollisions();

                    if (!gameOver) {
                        score++;
                        displayGrid();
                        System.out.println("Score: " + score + " | Level: " + level + " | Lives: " + lives);
                    }

                }
            }
        }

        scanner.close();


    }

    private boolean processMove(char move) {
        int newRow = playerRow;
        int newCol = playerCol;

        switch (move) {
            case  'W': // UP
                newRow = Math.max(0, playerRow - 1);
                break;

            case 'A': // Left
                newCol = Math.max(0, playerCol - 1);
                break;

            case 'S': // Down
                newRow = Math.min(GRID_SIZE - 1, playerRow + 1);
                break;

            case 'D':
                newCol = Math.min(GRID_SIZE - 1, playerCol + 1);
                break;

            default:
                System.out.println("Invalid Move! Use W, A, S, D to move");
                return false;


        }

        grid[playerRow][playerCol] = EMPTY;
        playerRow = newRow;
        playerCol = newCol;

        if (grid[playerRow][playerCol] == POWERUP) {
            lives++;
            System.out.println("Powerup collected you gained an extra life! ");
        }

        grid[playerRow][playerCol] = PLAYER;
        return true;
    }

    private void updateBombs() {
        List<Bomb> bombsToRemove = new ArrayList<>();
        List<Explosion> newExplosions = new ArrayList<>();

        for (Bomb bomb: bombs) {
            bomb.timer--;

            if (bomb.timer <= 0) {
                bombsToRemove.add(bomb);

                int[][] directions = {{0, 0}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}};
                for (int[] dir: directions) {
                    int newRow = bomb.row + dir[0];
                    int newCol = bomb.col + dir[1];

                    if (newRow >= 0 && newRow < GRID_SIZE && newCol >= 0 && newCol < GRID_SIZE) {
                        Explosion explosion = new Explosion(newRow, newCol);
                        newExplosions.add(explosion);

                        if (!(newRow == playerRow && newCol == playerCol)) {
                            grid[newRow][newCol] = EXPLOSION;
                        }
                    }
                }
            }
        }

        bombs.removeAll(bombsToRemove);
        explosions.addAll(newExplosions);
    }

    public void updateExplosions() {
        List<Explosion> explosionsToRemove = new ArrayList<>();

        for (Explosion explosion: explosions) {
            explosion.duration--;
                if (explosion.duration <= 0) {
                    explosionsToRemove.add(explosion);
                    if (!(explosion.row == playerRow && explosion.col == playerCol) ) {
                        grid[explosion.row][explosion.col] = EMPTY;
                    }
                }
        }

        explosions.removeAll(explosionsToRemove);
    }

    private void spawnBombs() {
        // No. of Bombs to increase as per the level

        int numBombsToSpawn = level;


        for (int i = 0; i < numBombsToSpawn; i++) {
            int attempts = 0;
            boolean placed = false;

            while (!placed && attempts < 20) {
                int row = random.nextInt(GRID_SIZE);
                int col = random.nextInt(GRID_SIZE);

                if (grid[row][col] == EMPTY) {
                    grid[row][col] = BOMB;
                    bombs.add(new Bomb(row, col));
                    placed = true;
                }

                attempts++;
            }
        }
    }

    private void spawnPowerup() {
        int attempts = 0;
        boolean placed = false;

        while (!placed && attempts < 20) {
            int row = random.nextInt(GRID_SIZE);
            int col = random.nextInt(GRID_SIZE);

            if(grid[row][col] == EMPTY) {
                grid[row][col] = POWERUP;
                placed = true;

            }

            attempts++;
        }
    }

    private void levelUp() {
        level++;
        System.out.println("Level Up You have reached level " + level + "!");
        System.out.println("Bombs will be now more frequent");
    }

    private void checkCollisions() {
        boolean hitBomb = false;

        for (Bomb bomb : bombs) {
            if (bomb.row == playerRow && bomb.col == playerCol) {
                hitBomb = true;
                break;
            }
        }

        boolean hitExplosion = false;
        for (Explosion explosion: explosions) {
            if (explosion.row == playerRow && explosion.col == playerCol) {
                hitExplosion = true;
                break;
            }
        }

        if (hitBomb || hitExplosion) {
            lives--;
            if (lives <= 0) {
                gameOver = true;
                System.out.println("BOOM! You have been bombarded");
                System.out.println("Game Over! Final Score: " + score);
                displayGrid();
            }
            else {
                System.out.println("\n Warning You were hit by a " +  (hitBomb ? "bomb": "explosion"));
                System.out.println("Lives Remaining: " + lives);

            }
        }
    }

    private void displayGrid() {
        System.out.println("\n Current Grid: ");

        System.out.print(" ");
        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i + " ");

        }

        System.out.println();

        for (int i = 0; i < GRID_SIZE; i++) {
            System.out.print(i + " ");

            for (int j = 0; j < GRID_SIZE; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }





    public static void main(String[] args) {

        EnhancedBombardedManGameMine game = new EnhancedBombardedManGameMine();
        game.play();


    }
}