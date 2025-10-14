package controller;

import errors.ConflictException;
import errors.NotFoundException;
import model.Book;
import service.BookService;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookController {
    private final BookService bookService;

    public BookController(Connection connection){
        this.bookService = new BookService(connection);
    }

    public void createBook(Book book) {
        try {
            bookService.createBook(book);
            JOptionPane.showMessageDialog(null, "Book created successfully");
        } catch (ConflictException e) {
            JOptionPane.showMessageDialog(null, "Conflict when creating book: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error when creating book: " + e.getMessage());
        }
    }

    public void getBookById(int id) {
        try {
            Book book = bookService.getBookById(id);
            if (book == null) {
                throw new NotFoundException("Book not found with ID: " + id);
            }
            JOptionPane.showMessageDialog(null, book.toString());
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error retrieving book: " + e.getMessage());
        }
    }

    public void listBooks() {
        try {
            List<Book> books = bookService.getAllBooks();
            if (books.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No books found.");
            } else {
                StringBuilder sb = new StringBuilder("📚 Book List:\n");
                for (Book b : books) {
                    sb.append(b).append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error listing books: " + e.getMessage());
        }
    }

    public void updateBook(Book book) {
        try {
            bookService.updateBook(book);
            JOptionPane.showMessageDialog(null, "Book updated successfully");
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(null, "Book not found: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating book: " + e.getMessage());
        }
    }

    public void deleteBook(int id) {
        try {
            bookService.deleteBook(id);
            JOptionPane.showMessageDialog(null, "Book deleted successfully");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error deleting book: " + e.getMessage());
        }
    }
}
