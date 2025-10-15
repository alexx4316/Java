package dao;

import model.Book;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {
    void create(Book book) throws SQLException;
    Book getById(int id) throws SQLException;
    void update(Book book) throws SQLException;
    void delete(int id) throws SQLException;
    List<Book> getAll() throws SQLException;
}
