package main.java.com.tienda.model;

import java.math.BigDecimal;

public class Food extends Article{
    private int daysToExpire;

    // Traemos los datos del padre le agregamos diasParaCaducar
    public Food(String name, BigDecimal price, int stock, int daysToExpire) {
        super(name, price, stock);
        this.daysToExpire = daysToExpire;
    }

    // Setters y Getters
    public int getDaysToExpire() {
        return daysToExpire;
    }

    public void setDaysToExpire(int diasParaCaducar) {
        this.daysToExpire = daysToExpire;
    }

    // Traemos el metodo del padre
    @Override
    public String getDescription() {
        return String.format("%s (Alimento) - caduca en %d dias", getName(),  daysToExpire);
    }
}
