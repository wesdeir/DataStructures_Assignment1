import java.util.Scanner;
public class Assign1 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library library = new Library();
        while (true) {
            displayMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    library.addBook(scanner);
                    break;
                case "2":
                    System.out.print(library.toString());
                    break;
                case "3":
                    library.borrowBook(scanner);
                    break;
                case "4":
                    library.returnBook(scanner);
                    break;
                case "5":
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Incorrect value entered");
            }
        }
    }

    /** Prints the main menu exactly as per assignment spec */
    public static void displayMenu() {
        System.out.println("Please select one of the following:");
        System.out.println("1: Add Book to Library");
        System.out.println("2: Display Current Library Catalog");
        System.out.println("3: Borrow Book(s)");
        System.out.println("4: Return Book(s)");
        System.out.println("5: To Exit");
        System.out.print("> ");
    }
}