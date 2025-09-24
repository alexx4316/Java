package main.java.com.tienda.model;

public class Food extends Article{
    private int daysToExpire;

    // Traemos los datos del padre le agregamos diasParaCaducar
    public Food(String name, String code, double price, int stock, String daysToExpire) {
        super(name, code, price, stock);
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
