package view;

import javax.swing.*;
import controller.*;

import java.sql.Connection;

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

    public void showMenu() {
        String opt;
        do {
            opt = JOptionPane.showInputDialog("""
                    === LibroNova System ===
                    1. Book Management
                    2. Member Management
                    3. Loan Management
                    4. User Management
                    0. Exit
                    """);

            if (opt == null) return;

            switch (opt) {
                case "1" -> new BookView(bookController).showMenu();
                case "2" -> new PartnerView(partnerController).showMenu();
                case "3" -> new LoanView(loanController).showMenu();
                case "4" -> new UserView(userController).showMenu();
                case "0" -> JOptionPane.showMessageDialog(null, "See you later!");
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        } while (!"0".equals(opt));
    }
}