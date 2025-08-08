/*
 * NonFictionBook.java
 * Subclass of Book with an additional "field of study".
 */
import java.util.Scanner;

public class NonFictionBook extends Book {
    /** Specialized field identifying the subject area. */
    private String fieldOfStudy;

    /** Sets genre to "Non-Fiction". */
    public NonFictionBook() {
        super();
        genre = "Non-Fiction";
        fieldOfStudy = "";
    }

    /**
     * Reads shared fields then prompts for field of study.
     */
    @Override
    public boolean addBook(Scanner scanner) {
        super.addBook(scanner);
        System.out.print("Enter the field of study: ");
        fieldOfStudy = scanner.nextLine();
        return true;
    }

    /**
     * Includes the field of study in the output string.
     */
    @Override
    public String toString() {
        return String.format(
                "Book: %d %s %d Author: %s Genre: %s Field of Study: %s",
                bookCode, title, quantityInStock, author, genre, fieldOfStudy
        );
    }
}