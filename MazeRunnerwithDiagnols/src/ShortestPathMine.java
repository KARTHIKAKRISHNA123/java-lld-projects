import java.util.*;


public class ShortestPathMine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the matrix dimensions (n * n): "); //8
        int n = sc.nextInt();

        char[][] matrix = new char[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                matrix[i][j] = '0';
            }
        }

        System.out.println("Enter Adventurer position in the format of (row col)"); // 0 3
        int aRow = sc.nextInt(); //0
        int aCol = sc.nextInt(); //3

        matrix[aRow][aCol] = 'A';

        System.out.println("Enter Destination position in the format of (row col)"); // 4 5
        int dRow = sc.nextInt();
        int dCol = sc.nextInt();

        matrix[dRow][dCol] = 'D';

        System.out.println("Matrix");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }
        System.out.println();

        int shortestPath = findShortestPath(aRow, aCol, dRow, dCol);

        System.out.println("The Shortest path between A & D is " + shortestPath);




    }

    private static int findShortestPath(int startX, int startY, int endX, int endY) {
        return Math.max(Math.abs(endX - startX), Math.abs(endY - startY));
        //(0, 3)  //(4, 5) = (4 - 0), (5 - 3) = Max(4, 2) = 2

    }
}