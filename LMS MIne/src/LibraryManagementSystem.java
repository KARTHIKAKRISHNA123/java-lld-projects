import java.util.*;

class Book {
    String title;
    String author;
    String genre;
    boolean isAvailable;

    Book(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }
}

class Member {
    String username;
    List<Book> borrowedBooks;

    Member(String username) {
        this.username = username;
        this.borrowedBooks = new ArrayList<>();
    }


    boolean borrowBook(Book book) {
        if (borrowedBooks.size() < 5 && book.isAvailable) {
            borrowedBooks.add(book);
            book.isAvailable = false;
            return true;
        }

        return false;
    }

    void returnBook(Book book) {
        borrowedBooks.remove(book);
        book.isAvailable = true;

    }
}

public class LibraryManagementSystem {
    static Scanner scanner = new Scanner(System.in);
    static List<Book> books = new ArrayList<>();
    static List<Member> members = new ArrayList<>();

    static Member loggedInMember = null;
    static final String adminUsername = "admin";
    static final String adminPassword = "password";

    private static void loginAsAdmin() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        System.out.println("Enter Password: ");
        String password = scanner.nextLine();

        if (username.equals(adminUsername) && password.equals(adminPassword)) {
            System.out.println("Logged in as Admin!");
            adminMenu();
        }

        else {
            System.out.println("Invalid Admin Credentials! ");
        }
    }

    private static void loginAsUser() {
        System.out.println("Enter Username: ");
        String username = scanner.nextLine();
        loggedInMember = findOrCreateMember(username);
        System.out.println("Logged in as user!");
        userMenu();
    }

    private static Member findOrCreateMember(String username) {
        for (Member member: members) {
            if (member.username.equals(username)) {
                return member;
            }


        }
        Member newMember = new Member(username);
        members.add(newMember);
        return newMember;
    }

    private static void adminMenu() {
        while (true) {
            System.out.println("\n Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Update Book");
            System.out.println("3. Remove Book");
            System.out.println("4. Add Member");
            System.out.println("5. Display All Books");
            System.out.println("6. Display All Members");
            System.out.println("7. Exit");

            System.out.println("Enter Your Choice: ");
            int choice  = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: addBook();
                break;
                case 2: updateBook();
                break;
                case 3: removeBook();
                break;
                case 4: addMember();
                break;
                case 5:displayAllBooks();
                break;
                case 6: displayAllMembers();
                break;
                case 7: return;
                default:
                    System.out.println("Invalid Choice! Please Try Again.");
            }
        }
    }

    private static void userMenu() {
        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Borrow Book");
            System.out.println("2. Return Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Display All Members");
            System.out.println("5. Exit");

            System.out.println("Enter Your Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1: borrowBook(); break;
                case 2: returnBook(); break;
                case 3: displayAllBooks(); break;
                case 4: displayAllMembers(); break;
                case 5: return;
                default:
                    System.out.println("Invalid Choice! Please Try Again.");
            }
        }
    }

    private static void addBook() {
        System.out.println("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.println("Enter Book Author: ");
        String author = scanner.nextLine();
        System.out.println("Enter Book Genre: ");
        String genre = scanner.nextLine();
        books.add(new Book(title, author, genre));
        System.out.println("Book Added Successfully!");
    }

    private static void updateBook() {
        System.out.println("Enter book title to update: ");
        String title = scanner.nextLine();

        Book book = findBookByTitle(title);
        if (book != null) {
            System.out.println("Enter a new Author: ");
            book.author = scanner.nextLine();
            System.out.println("Enter new genre: ");
            book.genre = scanner.nextLine();
            System.out.println("Book updated Successfully");

        }

        else {
            System.out.println("Book Not Found!");
        }
    }

    private static void removeBook() {
        System.out.println("Enter book title to remove: ");
        String title = scanner.nextLine();

        Book book = findBookByTitle(title);
        if (book != null) {
            books.remove(book);
            System.out.println("Book Removed Successfully! ");
        }
        else {
            System.out.println("Book Not Found!");
        }
    }

    private static Book findBookByTitle(String title) {
        // Pass 1: Look for an AVAILABLE copy first
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title) && book.isAvailable) {
                return book;
            }
        }

        // Pass 2: If no available copy exists, return ANY copy
        // (so the system knows the book exists in the library, even if borrowed)
        for (Book book : books) {
            if (book.title.equalsIgnoreCase(title)) {
                return book;
            }
        }

        return null; // Book doesn't exist at all
    }

    private static void addMember() {
        System.out.println("Enter member username: ");
        String username = scanner.nextLine();
        members.add(new Member(username));
        System.out.println("Member Added Successfully!");
    }

    private static void borrowBook() {
        System.out.println("Enter Book Title To Borrow: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);

        if (book != null && loggedInMember.borrowBook(book)) {
            System.out.println("Book Borrowed Successfully!");
        }

        else {
            System.out.println("Book is Either unavailable or Borrow Limit is reached!");

        }
    }

    private static void returnBook() {
        System.out.println("Enter book title to return: ");
        String title = scanner.nextLine();
        Book book = findBookByTitle(title);

        // MODIFIED CONDITION:
        // Check if book exists AND if the logged-in user actually has it in their list
        if (book != null && loggedInMember.borrowedBooks.contains(book)) {
            loggedInMember.returnBook(book);
            System.out.println("Book Returned Successfully!");
        }
        else {
            // Updated error message to be more specific
            System.out.println("You cannot return this book (either it doesn't exist or you didn't borrow it).");
        }
    }

    private static void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No Books Available!");
        }

        else {
            System.out.println("\n List Books: ");
            for (Book book: books) {
                System.out.println("Title: " + book.title + ", Author: " + book.author + ", Genre: " + book.genre + ", Available: " + book.isAvailable);
            }
        }
    }

    private static void displayAllMembers() {
        if (members.isEmpty()) {
            System.out.println("No members exist");
        }
        else {
            System.out.println("\n List Members: ");
            for (Member member: members) {
                System.out.println("Username: " + member.username + ", Borrowed Books: " + member.borrowedBooks.size());
            }
        }
    }

    public static void main(String[] args) {
        books.add(new Book("Science", "John Doe", "Educational"));
        books.add(new Book("Social Studies", "Jane Smith", "Educational"));

        while (true) {
            System.out.println("Enter role (admin / user): ");
            String role = scanner.nextLine();


            if (role.equals("admin")) {
                loginAsAdmin();
            }
            else if (role.equals("user")) {
                loginAsUser();
            }
            else {
                System.out.println("Invalid Role. Please Try Again!");
            }
        }


    }


}