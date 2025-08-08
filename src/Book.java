/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This is the Book class. Essentially, we took the Book class from Assignment 1 and modified a large amount of it. Changes include but arent limited to:
 * Implementing Formatter to read values in from files as well as Scanner for console input. Implemented comparable to allow sorting by the book code at the last code block in this class (compareTo).
 * Student Name: Wesley Deir
 * Student Number: 041162206
 * Section Number: 300
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */

import java.util.Formatter;
import java.util.Scanner;

/**
 * Abstract base class for all book types in the library system.
 * Provides common fields (code, title, author, quantity) and operations
 * such as borrowing, returning, and comparing books.
 * Implements Comparable to allow sorting by bookCode.
 */
public abstract class Book implements Comparable<Book> {
    private int bookCode;   // unique code for each book
    private String title;   // title of the book
    private String author;  // author of the book
    private int quantity;   // number of copies available

    /**
     * Reads and sets basic book information: code, title, author, quantity.
     * Delegates to subclass to read extra fields.
     *
     * @param scanner  Scanner for reading input (console or file)
     * @param fromFile true if reading from file, false if console input
     * @return true if all fields read successfully, false otherwise
     */
    public boolean addBook(Scanner scanner, boolean fromFile) {
        // If bookCode is unset, read it first
        if (bookCode == 0) {
            if (!inputCode(scanner, fromFile)) {
                return false; // failed to read code
            }
        }

        // Read title, author, quantity depending on input source
        if (!fromFile) {
            System.out.print("Enter the title of the book: ");
            title = scanner.nextLine(); // read title

            System.out.print("Enter author of the book: ");
            author = scanner.nextLine(); // read author

            // ← Changed this block to loop until a valid integer is entered
            System.out.print("Enter the quantity of the book: ");
            while (true) {
                String line = scanner.nextLine();
                try {
                    quantity = Integer.parseInt(line);
                    if (quantity < 0) {
                        System.out.println("Invalid quantity, must be a positive integer.");
                                System.out.println("Enter the quantity of the book: ");
                                continue;
                    }
                    break; // valid → exit loop
                } catch (NumberFormatException e) {
                    System.out.println("Invalid quantity, must be integer.");
                    System.out.print("Enter the quantity of the book: ");
                }
            }
        } else {
            title = scanner.nextLine(); // read title from a file
            try {
                quantity = Integer.parseInt(scanner.nextLine()); // read quantity
            } catch (NumberFormatException e) {
                return false; // invalid number in file
            }
            author = scanner.nextLine(); // read author from a file
        }

        // Read subclass-specific extra fields
        if (!addBookExtra(scanner, fromFile)) {
            return false; // subclass failed to read its fields
        }

        return true; // all fields read successfully
    }

    /**
     * Reads and sets the book code.
     *
     * @param scanner  Scanner for input
     * @param fromFile true if reading from file, false if console input
     * @return true if code read successfully, false otherwise
     */
    public boolean inputCode(Scanner scanner, boolean fromFile) {
        if (fromFile) {
            // file-load: try once
            try {
                bookCode = Integer.parseInt(scanner.nextLine());
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        } else {
            // interactive: keep prompting until valid
            while (true) {
                System.out.print("Enter the code for the book: ");
                String line = scanner.nextLine();
                try {
                    bookCode = Integer.parseInt(line);
                    return true;   // if valid then exit loop
                } catch (NumberFormatException e) {
                    System.out.println("Invalid code, must be integer. Please try again.");
                }
            }
        }
    }



    /**
     * @return the book's unique code
     */
    public int getBookCode() {
        return bookCode;
    }

    /**
     * @return the book's title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @return the book's author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @return the number of copies available
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Sets the book's available quantity.
     *
     * @param q new quantity value
     */
    public void setQuantity(int q) {
        quantity = q; // update quantity
    }

    /**
     * Attempts to borrow a given number of copies.
     *
     * @param amount number to borrow
     * @return true if borrow succeeds, false if invalid amount or insufficient copies
     */
    public boolean borrowBook(int amount) {
        if (amount <= 0 || amount > quantity) {
            return false; // cannot borrow non-positive or more than available
        }
        quantity -= amount; // decrease available copies
        return true;
    }

    /**
     * Attempts to return a given number of copies.
     *
     * @param amount number to return
     * @return true if return succeeds, false if invalid amount
     */
    public boolean returnBook(int amount) {
        if (amount <= 0) {
            return false; // cannot return non-positive amount
        }
        quantity += amount; // increase available copies
        return true;
    }

    /**
     * Checks equality by comparing book codes.
     *
     * @param other another Book instance
     * @return true if both have the same bookCode
     */
    public boolean isEqual(Book other) {
        return this.bookCode == other.bookCode;
    }

    /**
     * Hook for subclasses to read their specific extra fields.
     *
     * @param scanner  Scanner for input
     * @param fromFile true if reading from file
     * @return true if extra fields read successfully
     */
    public abstract boolean addBookExtra(Scanner scanner, boolean fromFile);

    /**
     * Hook for subclasses to write their data to a Formatter for file output.
     *
     * @param writer Formatter for file writing
     */
    public abstract void outputBook(Formatter writer);

    /**
     * Displays basic book information to the console.
     */
    public void display() {
        // Print code, title, quantity, and author
        System.out.printf("Book: %d %s %d Author: %s%n", bookCode, title, quantity, author);
    }

    /**
     * Compares books for sorting by bookCode.
     *
     * @param other the other Book to compare against
     * @return negative if this < other, zero if equal, positive if this > other
     */
    @Override
    public int compareTo(Book other) {
        return Integer.compare(this.bookCode, other.bookCode);
    }
}