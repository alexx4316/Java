package main.java.com.tienda.model;

import java.math.BigDecimal;

public class Product extends Article{
    private String type;
    private int id;

    // Traemos los datos del padre le agregamos tipo
    public Product(String name, BigDecimal price, int stock, String type) {
        super(name, price, stock);
        this.type = type;
    }

    public Product() {
        super("", BigDecimal.ZERO, 0);
    }

    // Setter y Getters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    // Traemos el metodo del padre
    @Override
    public String getDescription() {
        return String.format("%s (Producto) - categoria: %s", getName(), type);
    }

    @Override
    public String toString(){
        return String.format("ID: %d | Name: %s | Price: $%,.2f | Stock: %d | Type: %s",
                id, getName(), getPrice().doubleValue(), getStock(), getType());
    }
}
