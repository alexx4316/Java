package view;

import controller.LoanController;
import model.Book;
import model.Loan;
import model.Partner;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoanView {

    private final LoanController controller;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public LoanView(LoanController controller) {
        this.controller = controller;
    }

    public void showMenu() {
        String option;
        do {
            option = JOptionPane.showInputDialog("""
                === Loan Management ===
                1. Register a loan
                2. Return a book
                3. List overdue loans
                0. Back
            """);

            if (option == null) return;

            switch (option) {
                case "1" -> registerLoan();
                case "2" -> returnBook();
                case "3" -> controller.listOverdueLoans();
                case "0" -> {}
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }

        } while (!"0".equals(option));
    }

    private void registerLoan() {
        try {
            int partnerId = Integer.parseInt(JOptionPane.showInputDialog("Enter Partner ID:"));
            int bookId = Integer.parseInt(JOptionPane.showInputDialog("Enter Book ID:"));
            String loanDateStr = JOptionPane.showInputDialog("Enter Loan Date (yyyy-MM-dd):");
            String returnDateStr = JOptionPane.showInputDialog("Enter Return Date (yyyy-MM-dd):");

            Date loanDate = dateFormat.parse(loanDateStr);
            Date returnDate = dateFormat.parse(returnDateStr);

            Partner partner = new Partner();
            partner.setId(partnerId);

            Book book = new Book();
            book.setId(bookId);

            Loan loan = new Loan(0, partner, book, loanDate, returnDate);
            controller.createLoan(loan);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "IDs must be numbers.");
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Invalid date format. Use yyyy-MM-dd.");
        }
    }

    private void returnBook() {
        try {
            int loanId = Integer.parseInt(JOptionPane.showInputDialog("Enter Loan ID to return:"));
            controller.returnBook(loanId);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Loan ID must be a number.");
        }
    }
}
