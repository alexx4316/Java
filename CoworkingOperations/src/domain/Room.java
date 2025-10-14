package domain;

public class Room {
    private int id;
    private String name;
    private String type;
    private double price;
    private boolean available;

    // Constructores
    public Room() {
    }

    public Room(String name, String type, double price, boolean available) {
        this.name = name;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    public Room(int id, String name, String type, double price, boolean available) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
        this.available = available;
    }

    // Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                ", available=" + (available ? "Yes" : "No");
    }
}
