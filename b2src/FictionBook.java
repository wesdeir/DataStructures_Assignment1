/*
 * FictionBook.java
 * Subclass of Book for fiction titles (borrowable).
 */
import java.util.Scanner;

public class FictionBook extends Book {
    /**
     * Constructor automatically sets genre to "Fiction".
     */
    public FictionBook() {
        super();
        genre = "Fiction";
    }

    /**
     * Uses the base addBook logic for shared fields.
     */
    @Override
    public boolean addBook(Scanner scanner) {
        super.addBook(scanner);
        return true;
    }
}