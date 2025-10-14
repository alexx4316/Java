package model;

public class Book {
    private int id;
    private String  isbn;
    private String title;
    private String author;
    private int publication_year;
    private int stock;

    public Book() {
    }

    public Book(int id, String isbn, String title, String author, int publication_year, int stock) {
        this.id = id;
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publication_year = publication_year;
        this.stock = stock;
    }

    public Book(String isbn, String title, String author, int publicationYear, int stock) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publication_year = publication_year;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublication_year() {
        return publication_year;
    }

    public void setPublication_year(int publicationYear) {
        this.publication_year = publicationYear;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", isbn='" + isbn + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publication_year=" + publication_year +
                ", stock=" + stock +
                '}';
    }
}
