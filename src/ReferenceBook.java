/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This is the ReferenceBook class. Implements the same features as it did previously but now also implements Formatter to read/write to a file in the correct format.
 * Student Name: Wesley Deir
 * Student Number: 041162206
 * Section Number: 300
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */


import java.util.Formatter;
import java.util.Scanner;

/**
 * Represents a reference book with an edition field.
 */
public class ReferenceBook extends Book {
    private String edition;

    @Override
    public boolean addBookExtra(Scanner scanner, boolean fromFile) {
        System.out.print(fromFile ? "" : "Enter the edition of the book: ");
        edition = scanner.nextLine();
        return true;
    }

    @Override
    public void outputBook(Formatter writer) {
        writer.format("r%n%d%n%s%n%d%n%s%n%s%n",
                getBookCode(), getTitle(), getQuantity(), getAuthor(), edition);
    }


    @Override
    public String toString() {
        return String.format("Book: %d %s %d Author: %s Genre: Reference Edition: %s",
                getBookCode(), getTitle(), getQuantity(), getAuthor(), edition);
    }

    public String getEdition() {
        return edition;
    }
}