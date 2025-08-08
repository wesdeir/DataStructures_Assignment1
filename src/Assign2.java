/**
 * CET - CS Academic Level 3
 * Declaration: I declare that this is my own original work and is free from Plagiarism
 * This is the driver class. It takes all the logic from Book, Library, ReferenceBook, NonFictionBook, FictionBook to bring everything together.
 * Also contains the DisplayMenu system for user interaction.Includes some basic error checking in the case that a user tries to enter an invalid menu option.
 * Student Name: Wesley Deir
 * Student Number: 041162206
 * Section Number: 300
 * Course: CST8130 - Data Structures
 * Professor: James Mwangi PhD.
 */

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main driver: presents menu until user exits.
 */
public class Assign2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Library lib = new Library();
        int choice;
        do {
            // Print out the DisplayMenu
            System.out.println("Please select one of the following:");
            System.out.println("1: Add Book to Library");
            System.out.println("2: Display Current Library Catalog");
            System.out.println("3: Borrow Book(s)");
            System.out.println("4: Return Book(s)");
            System.out.println("5: Search for Book");
            System.out.println("6: Save Library Catalog to File");
            System.out.println("7: Read Library Catalog from File");
            System.out.println("8: To Exit");
            System.out.print("> ");

            // Read and parse user choice
            choice = Integer.parseInt(scanner.nextLine());

            // Handle each option, using a mix of methods from assignment 1 and the new methods added in assignment 2
            try {
                switch (choice) {
                    case 1:
                        lib.addBook(scanner, false);
                        break;
                    case 2:
                        System.out.println(lib.toString());
                        break;
                    case 3:
                        lib.borrowBook(scanner);
                        break;
                    case 4:
                        lib.returnBook(scanner);
                        break;
                    case 5:
                        lib.searchForBook(scanner);
                        break;
                    case 6:
                        lib.saveToFile(scanner);
                        break;
                    case 7:
                        lib.readFromFile(scanner);
                        break;
                    case 8:
                        System.out.println("...Exiting...Program by Wesley Deir");
                        break;
                    default:
                        System.out.println("Invalid selection, choose 1-8.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input.");
            }
            } while (choice != 8) ;
            scanner.close();
    }
}