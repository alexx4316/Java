package dao;

import model.Loan;

import java.sql.SQLException;
import java.util.List;

public interface LoanDao {
    void create(Loan loan) throws SQLException;
    Loan getById(int id) throws SQLException;
    void update(Loan loan) throws SQLException;
    void delete(int id) throws SQLException;
    List<Loan> getAll() throws SQLException;
}
