import javax.swing.*;

// Definicion
public class Fruta {
    private int id;
    private String nombre;
    private double pesokg;
    private String color;
    private double precio;
    private String origen;
    private boolean esOrganica;

    // Constructor
    public Fruta(int id, String nombre, double pesokg, String color, double precio, String origen, boolean esOrganica) {
        this.id = id;
        setNombre(nombre);
        setPesokg(pesokg);
        setColor(color);
        setPrecio(precio);
        this.origen = origen;
        this.esOrganica = esOrganica;
    }

    // Getters y Setter con validaciones
    public int getId(){return id;}
    public  String getNombre(){return nombre;}
    public void setNombre(String nombre){
        if (nombre != null && !nombre.trim().isEmpty()){
            this.nombre = nombre;
        } else {
            this.nombre = "Desconocido";
        }
    }

    public double getPesokg(){return pesokg;}
    public void setPesokg(double pesokg){
        if (pesokg > 0){
            this.pesokg = pesokg;
        } else {
            this.pesokg = 0.1;
        }
    }

    public String getColor(){return color;}
    public void setColor(String color){
        this.color = (color != null && !color.trim().isEmpty()) ? color : "sin color";
    }

    public double getPrecio(){return precio;}
    public void setPrecio(double precio){
        this.precio = (precio >= 0) ? precio : 0;
    }

    public String getOrigen(){return origen;}
    public void setOrigen(String origen){
        this.origen = origen;
    }

    public boolean isEsOrganica(){return esOrganica;}
    public void setEsOrganica(boolean esOrganica){
        this.esOrganica = esOrganica;
    }

    // Imprimir contenido
    @Override
    public String toString() {
        return "🟢 Fruta registrada:\n" +
                "🔸 ID: " + id + "\n" +
                "🔸 Nombre: " + nombre + "\n" +
                "🔸 Peso (kg): " + pesokg + "\n" +
                "🔸 Color: " + color + "\n" +
                "🔸 Precio: $" + precio + "\n" +
                "🔸 Origen: " + origen + "\n" +
                "🔸 ¿Orgánica?: " + (esOrganica ? "Sí" : "No");
    }
}
