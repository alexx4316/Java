package Interfaces;

import Model.User;

import java.util.List;

public interface UserInterface {
    void addUser(User user);
    User findByEmail(String email);
    List<User> getAllUsers();
}
