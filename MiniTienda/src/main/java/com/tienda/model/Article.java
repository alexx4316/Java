package main.java.com.tienda.model;

import java.math.BigDecimal;

public abstract class Article {
    private String name;
    private BigDecimal price;
    private int stock;

    // Constructor
    public Article(String name, BigDecimal price, int stock) {
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Setters y Getters
    public String getName() {return name;}
    public void setName(String nombre) {this.name = nombre;}
    public BigDecimal getPrice() {return price;}
    public void setPrice(BigDecimal precio) {this.price = precio;}
    public int getStock() {return stock;}
    public void setStock(int stock) {this.stock = stock;}

    public abstract String getDescription();
}
