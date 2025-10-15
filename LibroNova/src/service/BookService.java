package service;

import dao.BookDao;
import dao.BookDaoJDBC;
import dao.DBConnection;
import errors.BadRequestException;
import errors.ConflictException;
import errors.NotFoundException;
import model.Book;
import util.ValidatorUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BookService {
    private final BookDao bookDao;

    // Traemos la conexion
    public BookService(Connection connection) {
            this.bookDao = new BookDaoJDBC(connection);
    }

    // Crear libro con validaciones
    public void createBook(Book book) throws SQLException, BadRequestException, ConflictException {
        // Validar que el objeto libro no sea nulo
        ValidatorUtil.validateNotNull(book, "The book object cannot be null.");

        // Validar que el ISBN sea correcto (10 o 13 dígitos)
        if (!ValidatorUtil.isValidISBN(book.getIsbn())) {
            throw new BadRequestException("The ISBN must have 10 or 13 digits.");
        }

        // Validar que el título no esté vacío
        if (book.getTitle() == null || book.getTitle().trim().isEmpty()) {
            throw new BadRequestException("The book title cannot be empty.");
        }

        // Validar que el stock sea positivo
        if (!ValidatorUtil.isPositiveNumber(book.getStock())) {
            throw new BadRequestException("The stock must be greater than zero.");
        }

        // Validar duplicados
        if (bookExists(book.getIsbn())) {
            throw new ConflictException("A book with this ISBN already exists.");
        }

        bookDao.create(book);
    }

    // Verificar si un libro ya existe por ISBN
    private boolean bookExists(String isbn) throws SQLException {
        List<Book> books = bookDao.getAll();
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                return true;
            }
        }
        return false;
    }

    // Obtener libro por ID
    public Book getBookById(int id) throws SQLException {
        if (!ValidatorUtil.isPositiveNumber(id)) {
            throw new BadRequestException("The ID must be a positive number.");
        }
        return bookDao.getById(id);
    }

    // Obtener todos los libros
    public List<Book> getAllBooks() throws SQLException {
        return bookDao.getAll();
    }

    // Actualizar un libro existente
    public void updateBook(Book book) throws SQLException, BadRequestException, NotFoundException {
        ValidatorUtil.validateNotNull(book, "The book cannot be null.");

        if (!ValidatorUtil.isPositiveNumber(book.getId())) {
            throw new BadRequestException("Invalid book ID.");
        }

        Book existing = bookDao.getById(book.getId());
        if (existing == null) {
            throw new NotFoundException("The book with ID " + book.getId() + " does not exist.");
        }

        if (!ValidatorUtil.isValidISBN(book.getIsbn())) {
            throw new BadRequestException("Invalid ISBN format.");
        }

        bookDao.update(book);
    }

    // Eliminar libro por ID
    public void deleteBook(int id) throws SQLException, NotFoundException {
        if (!ValidatorUtil.isPositiveNumber(id)) {
            throw new BadRequestException("Invalid book ID.");
        }

        Book existing = bookDao.getById(id);
        if (existing == null) {
            throw new NotFoundException("Book with ID " + id + " not found.");
        }

        bookDao.delete(id);
    }
}
