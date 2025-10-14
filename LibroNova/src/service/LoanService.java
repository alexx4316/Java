package service;

import dao.LoanDao;
import dao.LoanDaoJDBC;
import errors.BadRequestException;
import errors.InactivePartnerException;
import errors.NotFoundException;
import model.Loan;
import util.ValidatorUtil;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoanService {

    private final LoanDao loanDao = new LoanDaoJDBC();

    // Crear un nuevo préstamo
    public void createLoan(Loan loan) throws SQLException, BadRequestException, InactivePartnerException {
        // Validaciones básicas
        ValidatorUtil.validateNotNull(loan, "The loan cannot be null.");
        ValidatorUtil.validateNotNull(loan.getBook(), "The loan must have a valid book.");
        ValidatorUtil.validateNotNull(loan.getPartner(), "The loan must have a valid partner.");
        ValidatorUtil.validateNotNull(loan.getLoanDate(), "The loan date cannot be null.");
        ValidatorUtil.validateNotNull(loan.getReturnDate(), "The return date cannot be null.");

        // Validar que las fechas tengan sentido
        if (loan.getReturnDate().before(loan.getLoanDate())) {
            throw new BadRequestException("The return date cannot be earlier than the loan date.");
        }

        // Validar que el partner esté activo
        if (!loan.getPartner().isStatus()) {
            throw new InactivePartnerException("The partner is not active and cannot create a loan.");
        }

        // Crear el préstamo
        loanDao.create(loan);
    }


    // Listar préstamos vencidos
    public List<Loan> getOverdueLoans() throws SQLException {
        List<Loan> allLoans = loanDao.getAll();
        List<Loan> overdueLoans = new ArrayList<>();

        Date today = new Date();
        for (Loan loan : allLoans) {
            if (loan.getReturnDate() != null && loan.getReturnDate().before(today)) {
                overdueLoans.add(loan);
            }
        }

        return overdueLoans;
    }

    // Devolver un libro
    public void returnBook(int loanId) throws SQLException, NotFoundException {
        if (!ValidatorUtil.isPositiveNumber(loanId)) {
            throw new NotFoundException("Invalid loan ID.");
        }

        Loan loan = loanDao.getById(loanId);
        if (loan == null) {
            throw new NotFoundException("The loan with ID " + loanId + " does not exist.");
        }
        loanDao.delete(loanId);
    }
}
