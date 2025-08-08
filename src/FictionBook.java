/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 *This is the Fiction Book class. It essentially uses a toString method to print out and store FictionBook values that were passed by the user.
 * Now implements Formatter to write fields into a test file in the correct format using a lot of format specifiers.
 * Student Name: Wesley Deir
 * Student Number: 041162206
 * Section Number: 300
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */

import java.util.Formatter;
import java.util.Scanner;

/**
 * Represents a fiction book with no additional fields beyond the base class.
 */
public class FictionBook extends Book {
    @Override
    public boolean addBookExtra(Scanner scanner, boolean fromFile) {
        return true;
    }

    @Override
    public void outputBook(Formatter writer) {
        writer.format("f%n%d%n%s%n%d%n%s%n", getBookCode(), getTitle(), getQuantity(), getAuthor());
    }

    @Override
    public String toString() {
        return String.format("Book: %d %s %d Author: %s Genre: Fiction",
                getBookCode(), getTitle(), getQuantity(), getAuthor());
    }
}
