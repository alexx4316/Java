package dao;

import model.User;

import java.sql.SQLException;

public interface UserDao {
    void create(User user)throws SQLException;
    User getByEmail(String email) throws SQLException;
    boolean validateLogin(String email, String password) throws SQLException;
}
