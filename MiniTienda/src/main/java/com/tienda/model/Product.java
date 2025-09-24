package main.java.com.tienda.model;

public class Product extends Article{
    private String type;

    // Traemos los datos del padre le agregamos tipo
    public Product(String name, String code, double price, int stock, String type) {
        super(name, code, price, stock);
        this.type = type;
    }

    // Setter y Getters
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
}
