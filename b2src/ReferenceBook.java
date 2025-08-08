/*
 * ReferenceBook.java
 * Subclass of Book for reference materials (view-only) with an edition.
 */
import java.util.Scanner;

public class ReferenceBook extends Book {
    /** Edition detail, e.g. "3rd", "Revised". */
    private String edition;

    public ReferenceBook() {
        super();
        genre = "Reference";
        edition = "";
    }

    /**
     * Reads shared fields then prompts for edition.
     */
    @Override
    public boolean addBook(Scanner scanner) {
        super.addBook(scanner);
        System.out.print("Enter the edition of the book: ");
        edition = scanner.nextLine();
        return true;
    }

    /**
     * Includes the edition in the output string.
     */
    @Override
    public String toString() {
        return String.format(
                "Book: %d %s %d Author: %s Genre: %s Edition: %s",
                bookCode, title, quantityInStock, author, genre, edition
        );
    }
}