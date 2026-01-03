import java.util.*;
public class DungeonGameMine {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter grid size (n * n): "); //5 5
        int rows = sc.nextInt(); //5
        int cols = sc.nextInt(); // 5

        char[][] dungeon = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dungeon[i][j] = '.';
            }
        }

        System.out.println("Enter Adventure Position (rows cols): ");
        int aRow = sc.nextInt(); // 1
        int aCol = sc.nextInt(); // 1
        dungeon[aRow][aCol] = 'A';

        System.out.println("Enter Monster Position (rows cols): ");
        int mRow = sc.nextInt(); // 3
        int mCol = sc.nextInt(); // 2
        dungeon[mRow][mCol] = 'M';

        System.out.println("Enter Trigger Position (rows cols): ");
        int tRow = sc.nextInt(); // 0
        int tCol = sc.nextInt(); // 0
        dungeon[tRow][tCol] = 'T';

        System.out.println("Enter Treasure Position (rows cols): ");
        int trRow = sc.nextInt(); // 2
        int trCol = sc.nextInt(); // 4
        dungeon[trRow][trCol] = 'X';

        System.out.println("Enter the Number of pits");
        int pits = sc.nextInt(); // 2

        for (int i = 0; i < pits; i++) {
            System.out.println("Enter Pit " + (i + 1) + " Position (rows cols): ");
            int pRow = sc.nextInt(); // 2
            int pCol = sc.nextInt(); // 4
            dungeon[pRow][pCol] = 'P';

        }
        System.out.println("Dungeon Layout: ");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println(dungeon[i][j]);
            }
            System.out.println();
        }

        //Calculating distances without considering diagnols
        int adventureToTrigger = Math.abs(aRow - tRow) + Math.abs(aCol - tCol); //2
        int monsterToTrigger = Math.abs(mRow - tRow) + Math.abs(mCol - tCol); //5
        int adventureToTreasure = Math.abs(aRow - trRow) + Math.abs(aCol - trCol); //4
        int monsterToTreasure = Math.abs(mRow - trRow) + Math.abs(mCol - trCol); //3

        System.out.println("Result: ");

        if (adventureToTrigger < monsterToTrigger) {
            System.out.println("Adventurer reaches the trigger first!");
            System.out.println("Monster is Frozen! ");
            System.out.println("Adventurer can safely reach the treasure!");
            System.out.println("Mission possible Adventurer wins via trigger");
        }

        else if (adventureToTrigger > monsterToTrigger) {
            System.out.println("Monster reaches the Trigger first");
            System.out.println("Monster is Frozen");
            System.out.println("Trigger is deactivated");
            if (adventureToTreasure < monsterToTreasure) {
                System.out.println("Adventurer can still reach the treasure before the monster");

            }
            else {
                System.out.println("Monster will catch the Adeventurer");
            }

        }
        else  {
            //adventureToTrigger == monsterToTrigger both reach the trigger at the same time
            if (adventureToTreasure < monsterToTreasure) {
                System.out.println("Adventure can still reach the treasure before monster");

            }
            else {
                System.out.println("Monster will catch the adventurer");
            }
        }








        sc.close();




    }
}