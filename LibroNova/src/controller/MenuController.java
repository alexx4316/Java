package controller;

import javax.swing.*;
import java.sql.Connection;

public class MenuController {

    private final Connection connection;
    private final BookController bookController;
    private final LoanController loanController;
    private final PartnerController partnerController;

    public MenuController(Connection connection) {
        this.connection = connection;

        this.bookController = new BookController(connection);
        this.loanController = new LoanController();
        this.partnerController = new PartnerController();
    }

    public void showMainMenu() {
        String[] options = {"Book Management", "Loan Management", "Partner Management", "Exit"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option", "Main Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        switch (choice) {
            case 0:
                // controlador de libros
                showBookMenu();
                break;
            case 1:
                // controlador de préstamos
                showLoanMenu();
                break;
            case 2:
                // controlador de socios
                showPartnerMenu();
                break;
            case 3:
                System.exit(0);  // Salir de la aplicación
                break;
            default:
                break;
        }
    }

    // Menú de libros
    private void showBookMenu() {
        String[] options = {"Create book", "List books", "Return"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option", "Book Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        switch (choice) {
            case 0:
                //controlador de libros para crear un libro
                break;
            case 1:
                // controlador de libros para listar libros
                break;
            case 2:
                showMainMenu();
                break;
        }
    }

    // Menú de préstamos
    private void showLoanMenu() {
        String[] options = {"Create loan", "List overdue loans", "Return"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option", "Loan Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        switch (choice) {
            case 0:
                // controlador de préstamos para crear un préstamo
                break;
            case 1:
                // controlador de préstamos para listar préstamos vencidos
                break;
            case 2:
                showMainMenu();
                break;
        }
    }

    // Menú de socios
    private void showPartnerMenu() {
        String[] options = {"Create partner", "List partners", "Return"};
        int choice = JOptionPane.showOptionDialog(null, "Select an option", "Partner Menu",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);

        switch (choice) {
            case 0:
                // controlador de socios para crear un socio
                break;
            case 1:
                // controlador de socios para listar socios
                break;
            case 2:
                showMainMenu();
                break;
        }
    }
}
