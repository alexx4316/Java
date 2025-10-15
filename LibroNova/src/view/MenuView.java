package view;

import javax.swing.*;
import controller.*;
import model.User;
import java.sql.Connection;
import java.sql.SQLException;

public class MenuView {
    private final BookController bookController;
    private final PartnerController partnerController;
    private final LoanController loanController;
    private final UserController userController;

    private final Connection dbConnection;

    public MenuView(Connection connection) {
        this.dbConnection = connection;

        this.bookController = new BookController(connection);
        this.partnerController = new PartnerController();
        this.loanController = new LoanController();
        this.userController = new UserController();
    }

    // Método para login y obtener el usuario
    private User login() {
        try {
            String email = JOptionPane.showInputDialog("Enter your email:");
            String password = JOptionPane.showInputDialog("Enter your password:");

            if (email == null || password == null) return null; // Canceló

            User user = userController.login(email, password); // retorna User
            JOptionPane.showMessageDialog(null, "Welcome, " + user.getName() + "!");
            return user;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Login error: " + e.getMessage());
            return login(); // vuelve a intentar
        }
    }

    public void showMenu() throws SQLException {
        User loggedUser = login(); // primero autenticación
        if (loggedUser == null) return; // PARTNER canceló

        String role = loggedUser.getRole(); // "PARTNER" o "ADMIN"
        String opt;

        do {
            // Menú dinámico según rol
            if ("PARTNER".equalsIgnoreCase(role)) {
                opt = JOptionPane.showInputDialog("""
                        === LibroNova System (PARTNER) ===
                        1. Book Management
                        2. Loan Management
                        0. Exit
                        """);
            } else { // "PARTNER" o ADMIN
                opt = JOptionPane.showInputDialog("""
                        === LibroNova System (ADMIN) ===
                        1. Book Management
                        2. Member Management
                        3. Loan Management
                        4. User Management
                        0. Exit
                        """);
            }

            if (opt == null) return;

            switch (opt) {
                case "1" -> new BookView(bookController).showMenu();
                case "2" -> {
                    if ("PARTNER".equalsIgnoreCase(role)) {
                        new LoanView(loanController).showMenu();
                    } else {
                        new PartnerView(partnerController).showMenu();
                    }
                }
                case "3" -> new LoanView(loanController).showMenu();
                case "4" -> new UserView(userController).showMenu();
                case "0" -> JOptionPane.showMessageDialog(null, "See you later!");
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }

        } while (!"0".equals(opt));
    }
}
