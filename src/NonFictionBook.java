/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This is the NonFictionBook class. Implements the same features as it did previously but now also implements Formatter to read/write to a file in the correct format.
 * Student Name: Wesley Deir
 * Student Number: 041162206
 * Section Number: 300
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */

import java.util.Formatter;
import java.util.Scanner;

/**
 * Represents a non-fiction book with a specific field of study.
 */
public class NonFictionBook extends Book {
    private String fieldOfStudy;

    @Override
    public boolean addBookExtra(Scanner scanner, boolean fromFile) {
        System.out.print(fromFile ? "" : "Enter the field of study: ");
        fieldOfStudy = scanner.nextLine();
        return true;
    }

    @Override
    public void outputBook(Formatter writer) {
        writer.format("n%n%d%n%s%n%d%n%s%n%s%n",
                getBookCode(), getTitle(), getQuantity(), getAuthor(), fieldOfStudy);
    }

    public String getFieldOfStudy() {
        return fieldOfStudy;
    }

    @Override
    public String toString() {
        return String.format("Book: %d %s %d Author: %s Genre: Non-Fiction Field of Study: %s",
                getBookCode(), getTitle(), getQuantity(), getAuthor(), getFieldOfStudy());
    }
}
