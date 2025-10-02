package Interfaces;

import Model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserInterface {
    void addUser(User user) throws SQLException;
    User findByEmail(String email);
    List<User> getAllUsers() throws SQLException;
}
