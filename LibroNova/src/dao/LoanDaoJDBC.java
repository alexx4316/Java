package dao;

import model.Book;
import model.Loan;
import model.Partner;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDaoJDBC implements LoanDao {

    private static final String SQL_CREATE = "INSERT INTO loans (book_id, partner_id, loan_date, return_date) VALUES (?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE loans SET book_id = ?, partner_id = ?, loan_date = ?, return_date = ? WHERE id = ?";
    private static final String SQL_GET_BY_ID = "SELECT * FROM loans WHERE id = ?";
    private static final String SQL_GET_ALL = "SELECT * FROM loans";
    private static final String SQL_DELETE = "DELETE FROM loans WHERE id = ?";
    private static final String SQL_FIND_BOOK_BY_ID = "SELECT * FROM books WHERE id = ?";
    private static final String SQL_FIND_PARTNER_BY_ID = "SELECT * FROM partners WHERE id = ?";

    private Connection connection;

    public LoanDaoJDBC(Connection connection) {
        this.connection = connection;
    }

    public LoanDaoJDBC() {
    }

    @Override
    public void create(Loan loan) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_CREATE)) {
            ps.setInt(1, loan.getBook().getId());
            ps.setInt(2, loan.getPartner().getId());
            ps.setDate(3, new java.sql.Date(loan.getLoanDate().getTime()));
            ps.setDate(4, new java.sql.Date(loan.getReturnDate().getTime()));
            ps.executeUpdate();
            connection.commit();
        } catch (SQLException e) {
            if (connection != null) {
                connection.rollback();
            }
            throw new SQLException("Error creating loan", e);
        }
    }

    @Override
    public Loan getById(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_GET_BY_ID)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int bookId = rs.getInt("book_id");
                int partnerId = rs.getInt("partner_id");

                Book book = getBookById(bookId);
                Partner partner = getPartnerById(partnerId);

                return new Loan(
                        rs.getInt("id"),
                        partner,
                        book,
                        rs.getDate("loan_date"),
                        rs.getDate("return_date")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException("Error finding loan", e);
        }
    }

    @Override
    public void update(Loan loan) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_UPDATE)) {
            ps.setInt(1, loan.getBook().getId());
            ps.setInt(2, loan.getPartner().getId());
            ps.setDate(3, new java.sql.Date(loan.getLoanDate().getTime()));
            ps.setDate(4, new java.sql.Date(loan.getReturnDate().getTime()));
            ps.setInt(5, loan.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error updating loan", e);
        }
    }

    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Error deleting loan", e);
        }
    }

    @Override
    public List<Loan> getAll() throws SQLException {
        List<Loan> loans = new ArrayList<>();
        try (Statement st = connection.createStatement();
             ResultSet rs = st.executeQuery(SQL_GET_ALL)) {

            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                int partnerId = rs.getInt("partner_id");

                Book book = getBookById(bookId);
                Partner partner = getPartnerById(partnerId);

                loans.add(new Loan(
                        rs.getInt("id"),
                        partner,
                        book,
                        rs.getDate("loan_date"),
                        rs.getDate("return_date")
                ));
            }
        } catch (SQLException e) {
            throw new SQLException("Error retrieving loans", e);
        }
        return loans;
    }

    private Book getBookById(int bookId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_FIND_BOOK_BY_ID)) {
            ps.setInt(1, bookId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Book(
                        rs.getInt("id"),
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("publication_year"),
                        rs.getInt("stock")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException("Error finding book by ID", e);
        }
    }

    private Partner getPartnerById(int partnerId) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQL_FIND_PARTNER_BY_ID)) {
            ps.setInt(1, partnerId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Partner(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("role"),
                        rs.getString("password"),
                        rs.getBoolean("status"),
                        rs.getDate("register_date")
                );
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new SQLException("Error finding partner by ID", e);
        }
    }
}
