package model;

import java.util.Date;

public class Loan {
    private int id;
    private Partner partner;
    private  Book book;
    private Date loanDate;
    private Date returnDate;

    public Loan() {
    }

    public Loan(int id, Partner partner, Book book, Date loanDate, Date returnDate) {
        this.id = id;
        this.partner = partner;
        this.book = book;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Partner getPartner() {
        return partner;
    }

    public void setPartner(Partner partner) {
        this.partner = partner;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", partner=" + partner +
                ", book=" + book +
                ", loanDate=" + loanDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
