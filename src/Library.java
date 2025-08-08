/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This is the Library class. It implements a plethora of new features in comparison with the original Assignment1 Libarary class. Now implements FileWriter for File I/O, uses a binary search to find books by their bookcode after theyve been sorted using the Comparable method. As well as some other features.
 * SearchBook, SaveToFile, SearchForFile, Binary search etc.
 * Student Name: Wesley Deir
 * Student Number: 041162206
 * Section Number: 300
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * This class represents a library and manages the catalog of books.
 * It supports operations like adding, borrowing, returning, saving, and loading books.
 */
public class Library {
    private List<Book> catalog;

    /**
     * Constructs an empty Library with an ArrayList catalog.
     */
    public Library() {
        catalog = new ArrayList<>(); // initialize the catalog list
    }

    /**
     * Checks whether the given book already exists in the catalog.
     * @param book the Book to check
     * @return index in catalog if exists and equal, otherwise -1
     */
    public int alreadyExists(Book book) {
        int index = binarySearch(book.getBookCode()); // search by book code
        if (index != -1 && catalog.get(index).isEqual(book)) {
            return index; // found matching book
        }
        return -1; // not found or not equal
    }

    /**
     * Adds a book to the catalog, either from user input or file.
     * @param keyboard Scanner for input source (console or file)
     * @param fromFile true if reading from file, false if reading from console
     * @return true if book added successfully, false on error or duplicate
     */
    public boolean addBook(Scanner keyboard, boolean fromFile) {
        Book book = null;
        // Prompt until a valid book type is selected
        do {
            if (!fromFile) {
                System.out.print("Do you wish to add a Fiction(f), Non-Fiction(n), or Reference(r) book? ");
            }
            String input;

            if (fromFile) {
                if (!keyboard.hasNextLine()) return false; // no more lines in file
                input = keyboard.nextLine().trim().toLowerCase(); // read type code from file
            } else {
                input = keyboard.nextLine().trim().toLowerCase(); // read type code from console
            }

            switch (input) {
                case "f":
                    book = new FictionBook(); // create FictionBook
                    break;
                case "n":
                    book = new NonFictionBook(); // create NonFictionBook
                    break;
                case "r":
                    book = new ReferenceBook(); // create ReferenceBook
                    break;
                default:
                    if (!fromFile) System.out.println("Error... invalid book type"); // invalid selection
            }
        } while (book == null); // repeat until valid type selected

        // Read and validate book code
        if (!book.inputCode(keyboard, fromFile)) return false;
        // Check for duplicates
        if (alreadyExists(book) != -1) {
            System.out.println("Book already exists"); // duplicate found
            if (fromFile) {
                System.out.println("Error encountered while reading the file, aborting..."); // file error
            }
            return false; // abort addition
        }

        // Read additional fields (title, author, etc.)
        if (!book.addBook(keyboard, fromFile)) return false;

        // Add to catalog and sort by book code
        catalog.add(book);
        catalog.sort(Comparator.comparingInt(Book::getBookCode));
        return true; // success
    }

    /**
     * Borrows a specified quantity of a book by code.
     * @param keyboard Scanner for console input
     * @return true if borrow successful, false on error
     */
    public boolean borrowBook(Scanner keyboard) {
        if (catalog.isEmpty()) {
            System.out.println("Error...could not borrow book"); // no books available
            return false;
        }

        System.out.print("Enter the code for the book: ");
        if (!keyboard.hasNextInt()) {
            keyboard.nextLine(); // consume invalid input
            System.out.println("Error...could not borrow book"); // invalid code
            return false;
        }
        int code = keyboard.nextInt(); // get code
        keyboard.nextLine(); // consume newline

        int index = binarySearch(code); // find book index
        // Ensure book exists and is not a reference type
        if (index == -1 || catalog.get(index) instanceof ReferenceBook) {
            System.out.println("Error...could not borrow book");
            return false;
        }

        System.out.print("Enter valid quantity to borrow: ");
        if (!keyboard.hasNextInt()) {
            keyboard.nextLine(); // consume invalid
            System.out.println("Error...could not borrow book"); // invalid quantity
            return false;
        }
        int amount = keyboard.nextInt(); // get amount
        keyboard.nextLine(); // consume newline

        // Attempt to borrow; print error if fails
        if (!catalog.get(index).borrowBook(amount)) {
            System.out.println("Error...could not borrow book");
            return false;
        }

        return true; // borrow succeeded
    }

    /**
     * Returns a specified quantity of a book by code.
     * @param keyboard Scanner for console input
     * @return true if return successful, false on error
     */
    public boolean returnBook(Scanner keyboard) {
        if (catalog.isEmpty()) {
            System.out.println("Error...could not return book"); // no books available
            return false;
        }

        System.out.print("Enter the code for the book: ");
        if (!keyboard.hasNextInt()) {
            keyboard.nextLine(); // consume invalid input
            System.out.println("Error...could not return book"); // invalid code
            return false;
        }
        int code = keyboard.nextInt(); // get code
        keyboard.nextLine(); // consume newline

        int index = binarySearch(code); // find book index
        if (index == -1) {
            System.out.println("Error...could not return book"); // not found
            return false;
        }

        System.out.print("Enter valid quantity to return: ");
        if (!keyboard.hasNextInt()) {
            keyboard.nextLine(); // consume invalid
            System.out.println("Error...could not return book"); // invalid quantity
            return false;
        }
        int amount = keyboard.nextInt(); // get amount
        keyboard.nextLine(); // consume newline

        // Attempt to return; print error if fails
        if (!catalog.get(index).returnBook(amount)) {
            System.out.println("Error...could not return book");
            return false;
        }

        return true; // return succeeded
    }

    /**
     * Searches for a book by its code and displays it if found.
     * @param keyboard Scanner for console input
     * @return true if book found and displayed, false otherwise
     */
    public boolean searchBook(Scanner keyboard) {
        System.out.print("Enter the code for the book: ");
        if (!keyboard.hasNextInt()) {
            keyboard.nextLine(); // consume invalid
            System.out.println("Invalid code..."); // invalid code
            return false;
        }
        int code = keyboard.nextInt(); // get code
        keyboard.nextLine(); // consume newline

        int index = binarySearch(code); // find index
        if (index == -1) {
            System.out.println("Code not found in catalog..."); // not found
            return false;
        }

        catalog.get(index).display(); // display book details
        return true; // displayed successfully
    }

    /**
     * Delegate method for searchBook to match interface.
     * @param scanner Scanner for input
     */
    public void searchForBook(Scanner scanner) {
        searchBook(scanner); // call searchBook internally
    }

    /**
     * Performs a binary search on the sorted catalog for a given code.
     * @param code the book code to search for
     * @return index of matching book, or -1 if not found
     */
    private int binarySearch(int code) {
        int low = 0, high = catalog.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2; // compute midpoint
            int midCode = catalog.get(mid).getBookCode(); // get code at mid
            if (midCode == code) return mid; // found
            if (midCode < code) low = mid + 1; // search upper half
            else high = mid - 1; // search lower half
        }
        return -1; // not found
    }

    /**
     * Saves the current catalog to a specified file.
     * @param keyboard Scanner for console input of filename
     * @return true if save successful, false on IO error
     */
    public boolean saveToFile(Scanner keyboard) {
        System.out.print("Enter the filename to save to: ");
        String filename = keyboard.nextLine(); // read filename

        try (FileWriter writer = new FileWriter(filename)) {
            for (Book book : catalog) {
                if (book instanceof FictionBook) writer.write("f\n"); // fiction marker
                else if (book instanceof NonFictionBook) writer.write("n\n"); // nonfiction marker
                else if (book instanceof ReferenceBook) writer.write("r\n"); // reference marker
                else continue; // skip unknown types

                writer.write(book.getBookCode() + "\n"); // write code
                writer.write(book.getTitle() + "\n"); // write title
                writer.write(book.getQuantity() + "\n"); // write quantity
                writer.write(book.getAuthor() + "\n"); // write author

                if (book instanceof NonFictionBook) {
                    writer.write(((NonFictionBook) book).getFieldOfStudy() + "\n"); // write field
                } else if (book instanceof ReferenceBook) {
                    writer.write(((ReferenceBook) book).getEdition() + "\n"); // write edition
                }
            }
        } catch (IOException e) {
            System.out.println("Error...could not save file"); // handle write error
            return false;
        }

        System.out.println("Successfully saved to " + filename); // success message
        return true; // saved
    }

    /**
     * Reads a catalog from a specified file and loads books into the library.
     * @param keyboard Scanner for console input of filename
     * @return true if read successful, false on error or file not found
     */
    public boolean readFromFile(Scanner keyboard) {
        System.out.print("Enter filename to read from: ");
        String filename = keyboard.nextLine(); // read filename

        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                if (!addBook(fileScanner, true)) {
                    return false; // stop on error
                }
            }
            System.out.println("Successfully read from " + filename); // success message
            return true; // read completed
        } catch (IOException e) {
            System.out.println("File Not Found, ignoring..."); // file not found
            return false;
        }
    }

    /**
     * Prints the formatted library catalog as a String.
     * @return formatted catalog listing
     */
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("Library Catalog:\n"); // header
        for (int i = 0; i < catalog.size(); i++) {
            builder.append(catalog.get(i).toString()).append("\n"); // append each book
        }
        return builder.toString(); // return result
    }
}