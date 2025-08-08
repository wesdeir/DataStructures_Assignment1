/*
 * Book.java
 * Author: ChatGPT
 * Student Number: N/A
 * Assignment: 1
 * Date: 2025-05-29
 *
 * Base class representing any book in the library system.
 * Holds common fields and operations for all book types.
 */
import java.util.Scanner;

public class Book {
    /** Unique integer code for this book. */
    protected int bookCode;
    /** Title of the book. */
    protected String title;
    /** Author of the book. */
    protected String author;
    /** Genre set by subclasses: "Fiction", "Non-Fiction", or "Reference". */
    protected String genre;
    /** Current stock count (copies available). */
    protected int quantityInStock;
    /** Original stock count to prevent over-return. */
    protected int originalQuantityInStock;

    /**
     * Default constructor initializes all fields to safe defaults.
     */
    public Book() {
        this.bookCode = 0;
        this.title = "";
        this.author = "";
        this.genre = "";
        this.quantityInStock = 0;
        this.originalQuantityInStock = 0;
    }

    /**
     * Returns a one-line description of the book.
     * Format: Book: <code> <title> <qty> Author: <author> Genre: <genre>
     */
    @Override
    public String toString() {
        return String.format(
                "Book: %d %s %d Author: %s Genre: %s",
                bookCode,          // display book code
                title,             // display title
                quantityInStock,   // display available copies
                author,            // display author name
                genre              // display genre/category
        );
    }

    /**
     * Adjusts stock by the given amount.
     * @param amount positive to return, negative to borrow
     * @return true if resulting stock >= 0, false otherwise
     */
    public boolean updateQuantity(int amount) {
        // Prevent stock dropping below zero
        if (quantityInStock + amount < 0) {
            return false;
        }
        quantityInStock += amount;
        return true;
    }

    /**
     * Compares this book's title and author to another book.
     * @param other book to compare
     * @return true if both title and author match exactly
     */
    public boolean isEqual(Book other) {
        if (other == null) return false;
        return this.title.equals(other.title)
                && this.author.equals(other.author);
    }

    /**
     * Prompts the user to enter a valid integer book code.
     * @param scanner Scanner for reading input
     * @return true if parse succeeds, false and prints "Invalid entry" otherwise
     */
    public boolean inputCode(Scanner scanner) {
        System.out.print("Enter the code for the book: ");
        String line = scanner.nextLine();
        try {
            bookCode = Integer.parseInt(line);
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Invalid entry");
            return false;
        }
    }


    /**
     * Reads title, author, and quantity from the user.
     * Loops until a non-negative integer quantity is entered.
     * @param scanner Scanner for reading input
     * @return always true once valid inputs are acquired
     */
    public boolean addBook(Scanner scanner) {
        // Title prompt
        System.out.print("Enter the title of the book: ");
        title = scanner.nextLine();
        // Author prompt
        System.out.print("Enter the author of the book: ");
        author = scanner.nextLine();
        // Quantity prompt + validation loop
        while (true) {
            System.out.print("Enter the quantity of the book: ");
            String input = scanner.nextLine();
            try {
                int qty = Integer.parseInt(input);
                if (qty < 0) {
                    System.out.println("Invalid entry");
                    continue; // retry on negative
                }
                quantityInStock = qty;
                originalQuantityInStock = qty;
                break; // valid
            } catch (NumberFormatException e) {
                System.out.println("Invalid entry"); // retry on parse error
            }
        }
        return true;
    }
}