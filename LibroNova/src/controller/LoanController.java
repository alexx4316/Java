package controller;

import errors.InactivePartnerException;
import model.Loan;
import service.LoanService;
import errors.NotFoundException;
import errors.BadRequestException;
import javax.swing.*;
import java.sql.SQLException;
import java.util.List;

public class LoanController {

    private final LoanService loanService = new LoanService();

    public void createLoan(Loan loan) {
        try {
            loanService.createLoan(loan);
            JOptionPane.showMessageDialog(null, "Loan created successfully.");
        } catch (BadRequestException e) {
            JOptionPane.showMessageDialog(null, "Bad Request: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Database error:" + e.getMessage());
        } catch (InactivePartnerException e) {
            throw new RuntimeException(e);
        }
    }

    public void listOverdueLoans() {
        try {
            List<Loan> overdueLoans = loanService.getOverdueLoans();
            StringBuilder loansList = new StringBuilder("Overdue loans: \n");
            for (Loan loan : overdueLoans) {
                loansList.append(loan).append("\n");
            }
            JOptionPane.showMessageDialog(null, loansList.toString());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error obtaining overdue loans: " + e.getMessage());
        }
    }

    public void returnBook(int loanId) {
        try {
            loanService.returnBook(loanId);
            JOptionPane.showMessageDialog(null, "Book returned successfully.");
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error returning book: " + e.getMessage());
        }
    }
}
