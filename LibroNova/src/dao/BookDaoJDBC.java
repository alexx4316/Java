package dao;

import model.Book;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDaoJDBC implements BookDao {
    private static final String SQL_CREATE = "INSERT INTO books (isbn, title, author, publication_year, stock) VALUES (?,?,?,?,?)";
    private static final String SQL_UPDATE = "UPDATE books SET isbn = ?, title = ?, author = ?, publication_year = ?, stock = ? WHERE id = ?";
    private static final String SQL_GET_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM books";
    private static final String SQL_DELETE = "DELETE FROM books WHERE id = ?";

    private final Connection connection;

    public BookDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Book book) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE)) {
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setInt(4, book.getPublication_year());
            ps.setInt(5, book.getStock());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error creating book", e);
        }
    }

    @Override
    public Book getById(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_GET_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publicationYear"),
                        rs.getInt("stock")
                );
            }
        } catch (SQLException e) {
            throw new SQLException("Error finding book", e);
        }
        return null;
    }

    @Override
    public void update(Book book) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            ps.setString(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setInt(4, book.getPublication_year());
            ps.setInt(5, book.getStock());
            ps.setInt(6, book.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error updating book", e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting book", e);
        }
    }

    @Override
    public List<Book> getAll() throws SQLException {
        List<Book> books = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQL_GET_ALL)) {
            while (rs.next()) {
                books.add(new Book(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publication_year"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Error finding books", e);
        }
        return books;
    }
}
