package controller;

import errors.BadRequestException;
import errors.UnauthorizedException;
import model.User;
import service.UserService;

import javax.swing.*;
import java.sql.SQLException;

public class UserController {

    private final UserService userService = new UserService();

    // Crear un nuevo usuario
    public void createUser(User user) {
        try {
            userService.createUser(user);
            JOptionPane.showMessageDialog(null, "User created successfully!");
        } catch (BadRequestException e) {
            JOptionPane.showMessageDialog(null, "Bad Request: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error while creating user: " + e.getMessage());
        }
    }

    // Iniciar sesión
    public User login(String email, String password) throws SQLException, BadRequestException, UnauthorizedException {
        User user = userService.login(email, password);
        JOptionPane.showMessageDialog(null, "Welcome, " + user.getName() + "!");
        return user;
    }
}
