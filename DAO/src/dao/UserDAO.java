package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void save(User user) throws SQLException;
    User findById(int id) throws SQLException;
    List<User> findAll() throws SQLException;
    boolean existsById(int id) throws SQLException;
    void deleteById(int id) throws SQLException;
}
