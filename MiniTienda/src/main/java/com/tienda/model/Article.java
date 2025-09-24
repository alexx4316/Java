package main.java.com.tienda.model;

public abstract class Article {
    private String name;
    private String code;
    private double price;
    private int stock;

    // Constructor
    public Article(String nombre, String codigo, double precio, int stock) {
        this.name = nombre;
        this.code = codigo;
        this.price = precio;
        this.stock = stock;
    }

    // Setters y Getters
    public String getName() {return name;}
    public void setName(String nombre) {this.name = nombre;}
    public String getCode() {return code;}
    public void setCode(String codigo) {this.code = codigo;}
    public double getPrice() {return price;}
    public void setPrice(double precio) {this.price = precio;}
    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}

    public abstract String getDescription();
}
