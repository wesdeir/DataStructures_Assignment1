import java.util.Scanner;

public class Library {
    /** Fixed capacity for up to 20 entries. */
    private Book[] catalog;
    /** Number of books currently in the catalog. */
    private int numBooks;

    /** Sets up an empty catalog. */
    public Library() {
        catalog = new Book[20];
        numBooks = 0;
    }

    /**
     * Formats the entire catalog with a blank line after each book.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numBooks; i++) {
            sb.append(catalog[i].toString()).append("\n\n");
        }
        return sb.toString();
    }

    /**
     * Checks for an existing book by matching title and author.
     * @param book candidate book
     * @return index if found, -1 otherwise
     */
    public int alreadyExists(Book book) {
        for (int i = 0; i < numBooks; i++) {
            if (catalog[i].isEqual(book)) return i;
        }
        return -1;
    }

    /**
     * Prompts to add a Fiction/Non-Fiction/Reference book.
     * Validates type, code, prevents duplicates, reads remaining fields,
     * and merges quantities for identical title+author.
     */
    public boolean addBook(Scanner scanner) {
        Book newBook;
        // 1) choose type
        while (true) {
            System.out.print("Do you wish to add a Fiction (f), Non-Fiction (n), or Reference (r) book? ");
            String choice = scanner.nextLine().trim().toLowerCase();
            if (choice.equals("f"))      { newBook = new FictionBook();    break; }
            if (choice.equals("n"))      { newBook = new NonFictionBook(); break; }
            if (choice.equals("r"))      { newBook = new ReferenceBook();  break; }
            System.out.println("Invalid entry");
        }

        // 2) prompt for & validate code
        do {
            // newBook.inputCode(scanner) loops until valid
        } while (!newBook.inputCode(scanner));

        // 3) now read title/author/quantity/etc.
        newBook.addBook(scanner);

        // 4) duplicate‐code check *after* full input
        for (int i = 0; i < numBooks; i++) {
            if (catalog[i].bookCode == newBook.bookCode) {
                System.out.println("Book code already exists.");
                return false;
            }
        }

        // 5) merge or append
        int idx = alreadyExists(newBook);
        if (idx != -1) {
            catalog[idx].updateQuantity(newBook.quantityInStock);
        } else {
            catalog[numBooks++] = newBook;
        }
        return true;
    }

    public boolean borrowBook(Scanner scanner) {
        if (numBooks == 0) {
            System.out.println("Error...could not borrow book");
            return false;
        }
        System.out.print("Enter the code for the book: ");
        String codeStr = scanner.nextLine();
        int code;
        try {
            code = Integer.parseInt(codeStr);
        } catch (NumberFormatException e) {
            System.out.println("Error...could not borrow book");
            return false;
        }
        for (int i = 0; i < numBooks; i++) {
            if (catalog[i].bookCode == code) {
                if (catalog[i] instanceof ReferenceBook) {
                    System.out.println("Reference books cannot be borrowed.");
                    System.out.println("Error...could not borrow book");
                    return false;
                }
                System.out.print("Enter valid quantity to borrow: ");
                String qtyStr = scanner.nextLine();
                int qty;
                try {
                    qty = Integer.parseInt(qtyStr);
                    if (qty < 1) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid quantity...");
                    System.out.println("Error...could not borrow book");
                    return false;
                }
                if (!catalog[i].updateQuantity(-qty)) {
                    System.out.println("Invalid quantity... not enough stock.");
                    System.out.println("Error...could not borrow book");
                    return false;
                }
                return true;
            }
        }
        System.out.println("Code not found in catalog...");
        System.out.println("Error...could not borrow book");
        return false;
    }

    public boolean returnBook(Scanner scanner) {
        if (numBooks == 0) {
            System.out.println("Error...could not return book");
            return false;
        }
        System.out.print("Enter the code for the book: ");
        String codeStr = scanner.nextLine();
        int code;
        try {
            code = Integer.parseInt(codeStr);
        } catch (NumberFormatException e) {
            System.out.println("Error...could not return book");
            return false;
        }
        for (int i = 0; i < numBooks; i++) {
            if (catalog[i].bookCode == code) {
                System.out.print("Enter valid quantity to return: ");
                String qtyStr = scanner.nextLine();
                int qty;
                try {
                    qty = Integer.parseInt(qtyStr);
                    if (qty < 1) throw new NumberFormatException();
                } catch (NumberFormatException ex) {
                    System.out.println("Invalid quantity...");
                    System.out.println("Error...could not return book");
                    return false;
                }
                if (catalog[i].quantityInStock + qty > catalog[i].originalQuantityInStock) {
                    System.out.println("Invalid return quantity... You cannot return more than you borrowed.");
                    System.out.println("Error...could not return book");
                    return false;
                }
                catalog[i].updateQuantity(qty);
                return true;
            }
        }
        System.out.println("Code not found in catalog...");
        System.out.println("Error...could not return book");
        return false;
    }
}
