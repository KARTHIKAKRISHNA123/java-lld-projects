

public class InMemoryFileManagementSystemMine {

    public static void main(String[] args) {
        FileManagementSystem fms = new FileManagementSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== File Management System ===");
            System.out.println("1. Create directory/file");      // Menu option 1
            System.out.println("2. List contents");              // Menu option 2
            System.out.println("3. Update file content");        // Menu option 3
            System.out.println("4. Rename directory/file");      // Menu option 4
            System.out.println("5. Delete directory/file");      // Menu option 5
            System.out.println("6. Restore deleted item");       // Menu option 6
            System.out.println("7. Exit");                       // Menu option 7
            System.out.print("Choose an option: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number (1-7).");
                sc.nextLine(); // Consume the bad input (junk string) so we don't loop infinitely
                continue;      // Restart the loop
            }

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.println("Enter Path: ");
                    String createPath = sc.nextLine();
                    System.out.println("Is It a directory? (true or false)");
                    boolean isDir = sc.nextBoolean();
                    sc.nextLine();
                    fms.create(createPath, isDir);
                    break;

                case  2:
                    System.out.println("Enter path to list (or root for root):  ");
                    String listPath = sc.nextLine();
                    fms.list(listPath);
                    break;

                case 3:
                    System.out.println("Enter file path to add new content: ");
                    String path = sc.nextLine();
                    System.out.println("Enter the content");
                    String content = sc.nextLine();
                    fms.updateContent(path, content);
                    break;

                case 4:
                    System.out.println("Enter file path to rename: ");
                    String path1 = sc.nextLine();
                    System.out.println("Enter the newName");
                    String newName = sc.nextLine();
                    fms.rename(path1, newName);
                    break;

                case 5:
                    System.out.println("Enter file path to delete: ");
                    String path2 = sc.nextLine();
                    fms.delete(path2);
                    break;

                case 6:
                    System.out.println("Enter name to restore: ");
                    String restoreName = sc.nextLine();
                    fms.restore(restoreName);
                    break;
                case 7:
                    System.out.println("Exiting...");
                    sc.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid Operation");



            }


        }

    }
}