import java.util.*;
public class MailManagementSystem {
    private final List<Mail> mails;
    private final Set<String> spamWords;
    private final Scanner scanner;

    public MailManagementSystem() {
        mails = new ArrayList<>();
        spamWords = new HashSet<>(Arrays.asList("lottery", "winner", "prize", "free", "urgent", "congratulations"));
        scanner = new Scanner(System.in);

    }

    public void storeMail() {

        System.out.println("Enter sender Mail: ");
        String sender = scanner.nextLine();
        System.out.println("Enter Receiver Mail: ");
        String receiver = scanner.nextLine();
        System.out.println("Enter Subject: ");
        String subject = scanner.nextLine();
        System.out.println("Enter content: ");
        String content = scanner.nextLine();

        Mail mail = new Mail(sender, receiver, subject, content);
        checkSpam(mail);
        mails.add(mail);
        System.out.println("Mail Stored Successfully!");
    }

    public void deleteMail() {
        // 1. Defensive Check: Don't ask for index if list is empty
        if (mails.isEmpty()) {
            System.out.println("List is empty. Nothing to delete.");
            return;
        }

        System.out.print("Enter mail index to delete (0 - "+ (mails.size() - 1) +"): ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (index >= 0 && index < mails.size()) {
            mails.remove(index);
            System.out.println("Mail Deleted Successfully!");
        } else {
            System.out.println("Invalid Mail Index!");
        }
    }

    public void addTag() {
        if (mails.isEmpty()) {
            System.out.println("List is empty. No mails to tag.");
            return;
        }
        System.out.println("Enter mail Index: ");
        int index = scanner.nextInt();
        scanner.nextLine();
        if (index >= 0 && index < mails.size()) {
            System.out.println("Enter tag: ");
            String tag = scanner.nextLine();
            mails.get(index).addTag(tag);
            System.out.println("Tag Added Successfully!");

        }

        else {
            System.out.println("Invalid Mail Index!");
        }
    }

    public void showStats() {
        System.out.println("\n Mail Statistics: ");
        System.out.println("Total Mails: " + mails.size());
        System.out.println("Enter Number of Recent Mails to Display: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        System.out.println("\n Recent" + n + "Mails: ");
        // 1. Calculate where to start printing
        //    Formula: Total Count minus How Many We Want
        int startIndex = mails.size() - n;

        // 2. Safety Check: If user asks for more mails than we have, start at 0
        if (startIndex < 0) {
            startIndex = 0;
        }

        // 3. Classic Loop: Start from that index and go to the end
        for (int i = startIndex; i < mails.size(); i++) {
            System.out.println(mails.get(i));
        }
    }

    private void checkSpam(Mail mail) {
        String content = mail.getContent().toLowerCase();
        for (String word: spamWords) {
            if (content.contains(word)) {
                mail.setSpam(true);
                break;
            }
        }

    }

    public void search() {
        System.out.println("Enter search query:");
        String query = scanner.nextLine(); // No need to lowerCase here, the Mail class handles it

        List<Mail> results = new ArrayList<>();

        // Classic Loop: Easy to read, easy to debug
        for (Mail mail : mails) {
            if (mail.matches(query)) {
                results.add(mail);
            }
        }

        if (results.isEmpty()) {
            System.out.println("No mails found matching the query.");
        } else {
            System.out.println("\nSearch Results:");
            for (Mail result : results) {
                System.out.println(result);
            }
        }
    }

    public void wildcardSearch() {
        System.out.println("Enter wildcard pattern (use * for any characters):");

        // 1. Prepare the pattern (User types "user*", we convert to Regex "user.*")
        String input = scanner.nextLine().toLowerCase();
        String pattern = input.replace("*", ".*");

        List<Mail> results = new ArrayList<>();

        // 2. Classic Loop: Clean and easy to debug
        for (Mail mail : mails) {
            if (mail.matchesWildcard(pattern)) {
                results.add(mail);
            }
        }

        // 3. Display Results
        if (results.isEmpty()) {
            System.out.println("No mails found matching the pattern.");
        } else {
            System.out.println("\nWildcard Search Results:");
            for (Mail result : results) {
                System.out.println(result);
            }
        }
    }


    public void displayMenu() {
        System.out.println("\n Mail Management System");
        System.out.println("1. Store mail");
        System.out.println("2. Delete mail");
        System.out.println("3. Add Tag");
        System.out.println("4. Show Statistics");
        System.out.println("5. Search");
        System.out.println("6. WildCard Search");
        System.out.println("7. Exit");
        System.out.println("Enter Your Choice: ");
    }

    public static void main(String[] args) {
        MailManagementSystem system = new MailManagementSystem();
        int choice;

        do {
            system.displayMenu();
            choice = system.scanner.nextInt();

            system.scanner.nextLine();

            switch (choice) {
                case 1:
                    system.storeMail();
                    break;
                case 2:
                    system.deleteMail();
                    break;

                case 3:
                    system.addTag();
                    break;

                case 4:
                    system.showStats();
                    break;

                case 5:
                    system.search();
                    break;
                case 6:
                    system.wildcardSearch();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid Choice! ");

            }

        }while (choice != 7);

        system.scanner.close();
    }




}
