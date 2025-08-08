/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism. However, I did use a general format to create this test class from a youtube video that i can provide upon request.
 * This is the driver class. It takes all the logic from Book, Library, ReferenceBook, NonFictionBook, FictionBook to bring everything together.
 * Also contains the DisplayMenu system for user interaction.Includes some basic error checking in the case that a user tries to enter an invalid menu option.
 * Student Name: Wesley Deir
 * Student Number: 041162206
 * Section Number: 300
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit 5 tests for Library.borrowBook(...) and Library.returnBook(...)
 * adapted to the new ArrayList-based implementation.
 */
public class Assign2Test {

    private Library lib;
    private static final int TEST_CODE = 100;

    @BeforeEach
    public void setUp() throws Exception {
        lib = new Library();
        injectSingleBook(lib, TEST_CODE, 5);
    }

    /**
     * Uses reflection to inject a single FictionBook into lib.catalog.
     */
    @SuppressWarnings("unchecked")
    private void injectSingleBook(Library lib, int code, int initialQuantity) throws Exception {
        // Create a FictionBook instance (concrete subclass)
        FictionBook book = new FictionBook();
        // Set private fields via reflection
        Field codeField = Book.class.getDeclaredField("bookCode");
        codeField.setAccessible(true);
        codeField.setInt(book, code);

        Field titleField = Book.class.getDeclaredField("title");
        titleField.setAccessible(true);
        titleField.set(book, "Test Title");

        Field authorField = Book.class.getDeclaredField("author");
        authorField.setAccessible(true);
        authorField.set(book, "Test Author");

        Field qtyField = Book.class.getDeclaredField("quantity");
        qtyField.setAccessible(true);
        qtyField.setInt(book, initialQuantity);

        // Access catalog ArrayList<Book>
        Field catalogField = Library.class.getDeclaredField("catalog");
        catalogField.setAccessible(true);
        ArrayList<Book> catalog = (ArrayList<Book>) catalogField.get(lib);
        catalog.clear(); // ensure empty
        catalog.add(book);
    }

    @Test
    public void testSuccessfulBorrowReducesStockByOne() throws Exception {
        // Capture console output
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Simulate user entering the book code
        Scanner scanner = new Scanner(TEST_CODE + "\n");
        lib.borrowBook(scanner);

        // Verify quantity decreased by one
        int qty = getInjectedBookQuantity(lib);
        assertEquals(4, qty, "Borrowing should decrement quantity by 1");

        // Verify correct console message
        String output = out.toString().trim();
        assertTrue(output.contains("Borrowed successfully."));
    }

    @Test
    public void testBorrowFailsWhenNoCopiesAvailable() throws Exception {
        // Inject zero quantity
        injectSingleBook(lib, TEST_CODE, 0);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Scanner scanner = new Scanner(TEST_CODE + "\n");
        lib.borrowBook(scanner);

        // Quantity should remain zero
        int qty = getInjectedBookQuantity(lib);
        assertEquals(0, qty, "Quantity should remain zero if no copies available");

        // Verify no-copies message
        String output = out.toString().trim();
        assertTrue(output.contains("No copies available to borrow."));
    }

    @Test
    public void testBorrowFailsWhenBookNotFound() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Use a code not in catalog
        Scanner scanner = new Scanner("999\n");
        lib.borrowBook(scanner);

        // Verify message and no exception thrown
        String output = out.toString().trim();
        assertTrue(output.contains("Code not found in catalog"));
    }

    @Test
    public void testReturnIncrementsStockByOne() throws Exception {
        // Start with initial setup quantity=5
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Scanner scanner = new Scanner(TEST_CODE + "\n");
        lib.returnBook(scanner);

        int qty = getInjectedBookQuantity(lib);
        assertEquals(6, qty, "Returning should increment quantity by 1");

        String output = out.toString().trim();
        assertTrue(output.contains("Returned successfully."));
    }

    @Test
    public void testReturnFailsWhenBookNotFound() throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        // Code not in catalog
        Scanner scanner = new Scanner("999\n");
        lib.returnBook(scanner);

        String output = out.toString().trim();
        assertTrue(output.contains("Code not found in catalog"));
    }

    /**
     * Reflectively reads the quantity of the injected book.
     */
    @SuppressWarnings("unchecked")
    private int getInjectedBookQuantity(Library lib) throws Exception {
        Field catalogField = Library.class.getDeclaredField("catalog");
        catalogField.setAccessible(true);
        ArrayList<Book> catalog = (ArrayList<Book>) catalogField.get(lib);
        Book b = catalog.get(0);
        Field qtyField = Book.class.getDeclaredField("quantity");
        qtyField.setAccessible(true);
        return qtyField.getInt(b);
    }
}