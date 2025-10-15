package view;

import controller.UserController;
import model.User;

import javax.swing.*;
import java.sql.SQLException;

public class UserView {

    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }


    public void showMenu() throws SQLException {
        String[] options = {"Register User", "Exit"};
        int choice;

        do {
            choice = JOptionPane.showOptionDialog(
                    null,
                    "User Management Menu",
                    "User Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]
            );

            switch (choice) {
                case 0 -> registerUser();
                case 1 -> JOptionPane.showMessageDialog(null, "Exiting user menu...");
                default -> {}
            }

        } while (choice != 1 && choice != JOptionPane.CLOSED_OPTION);
    }

    private void registerUser() {
        String name = JOptionPane.showInputDialog("Enter name:");
        String email = JOptionPane.showInputDialog("Enter email:");
        String password = JOptionPane.showInputDialog("Enter password:");

        if (name == null || email == null || password == null || name.isBlank() || email.isBlank() || password.isBlank()) {
            JOptionPane.showMessageDialog(null, "All fields are required.");
            return;
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        userController.createUser(user);
    }
}
