package view;

import controller.BookController;
import model.Book;

import javax.swing.*;

public class BookView {
    private final BookController controller;

    public BookView(BookController controller) {
        this.controller = controller;
    }

    public void showMenu() {
        String opcion;
        do {
            opcion = JOptionPane.showInputDialog("""
                === Book Management ===
                1. List books
                2. Register a book
                3. Edit a book
                4. Delete a book
                0. Back
            """);

            if (opcion == null) return;

            switch (opcion) {
                case "1" -> controller.listBooks();

                case "2" -> {
                    try {
                        String isbn = JOptionPane.showInputDialog("Enter ISBN:");
                        String title = JOptionPane.showInputDialog("Enter Title:");
                        String author = JOptionPane.showInputDialog("Enter Author:");
                        int year = Integer.parseInt(JOptionPane.showInputDialog("Enter Publication Year:"));
                        int stock = Integer.parseInt(JOptionPane.showInputDialog("Enter Stock:"));

                        Book book = new Book(isbn, title, author, year, stock);
                        controller.createBook(book);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Year and Stock must be numbers.");
                    }
                }

                case "3" -> {
                    try {
                        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID to edit:"));
                        String isbn = JOptionPane.showInputDialog("Enter new ISBN:");
                        String title = JOptionPane.showInputDialog("Enter new Title:");
                        String author = JOptionPane.showInputDialog("Enter new Author:");
                        int year = Integer.parseInt(JOptionPane.showInputDialog("Enter new Publication Year:"));
                        int stock = Integer.parseInt(JOptionPane.showInputDialog("Enter new Stock:"));

                        Book book = new Book(id, isbn, title, author, year, stock); // Constructor con ID
                        controller.updateBook(book);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "ID, Year and Stock must be numbers.");
                    }
                }

                case "4" -> {
                    try {
                        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID to delete:"));
                        controller.deleteBook(id);
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "ID must be a number.");
                    }
                }

                case "0" -> {} // salir

                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        } while (!"0".equals(opcion));
    }
}
