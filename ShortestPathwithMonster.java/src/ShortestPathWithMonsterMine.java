import java.util.*;
public class ShortestPathWithMonsterMine {

    static int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    static class Point {
        int x, y, dist;
        Point (int x, int y, int dist) {
            this.x = x;
            this.y = y;
            this.dist = dist;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a matrix size(n * n): "); //5
        int n = sc.nextInt();
        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            Arrays.fill(matrix[i], '0');
        }

        System.out.println("Enter the Position for Adventurer (A) (x y)"); //3 2

        int ax = sc.nextInt(); //3
        int ay = sc.nextInt(); //2

        matrix[ax][ay] = 'A';

        System.out.println("Enter the Position for Destination (D) (x y)"); //3 2

        int dx = sc.nextInt(); //3
        int dy = sc.nextInt(); //2

        matrix[dx][dy] = 'D';

        System.out.println("Enter the Number of Monsters (M): ");
        int m = sc.nextInt(); //2

        for (int i = 0; i < m; i++) {
            System.out.println("Enter Monster " + (i + 1) + " Position: ");
            int mx = sc.nextInt(); //3
            int my = sc.nextInt(); //2

            matrix[mx][my] = 'M';

        }

        System.out.println("Matrix");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }

        int result = findShortestPath(matrix, n);

        if (result != -1) {
            System.out.println("The Shortest Path is " + result);

        }

        else {
            System.out.println("A will die as A can't reach D");
        }

        sc.close();












    }

    private static int findShortestPath(char[][] matrix, int n) {
        Point start = null;
        Point end = null;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 'A')
                    start = new Point(i, j, 0); //Point(3, 2, 0)
                else if (matrix[i][j] == 'D')
                    end = new Point(i, j, 0); //Point(1, 4, 0)
            }
        }
        if (start == null || end == null) {
            return -1;
        }

        //BFS Implementation
        Queue<Point> queue = new LinkedList<>();
        boolean[][] visited = new boolean[n][n];
        queue.add(start); // queue = [(3, 2, 0)]

        //visited[3][2] = true;
        // F F F F F
        // F F F F F
        // F F F F F
        // F F T F F
        // F F F F F
        // F F F F F
        visited[start.x][start.y] = true;

        while (!queue.isEmpty()) {
            Point current = queue.poll();

            if (current.x == end.x && current.y == end.y)
                return current.dist;

            for (int i = 0; i < 8; i++) {
                int newX = current.x + dx[i];
                int newY = current.y + dy[i];
                // newX = 3 + dx[0] = 3 + (-1) = 2;
                // newY = 2 + dy[0] = 2 + (-1) = 1;

                if (isValid(newX, newY, n) && !visited[newX][newY] && matrix[newX][newY] != 'M') {
                    visited[newX][newY] = true;
                    queue.add(new Point(newX, newY, current.dist + 1));
                }

            }


        }

        return -1;





    }

    static boolean isValid(int x, int y, int n) {
        return x >= 0 && x < n && y >= 0 && y < n;
    }
}