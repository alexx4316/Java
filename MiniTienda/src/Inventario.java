import java.util.HashMap;
import java.util.ArrayList;

// Clase que representa un inventario de productos con nombre, precio y stock
public class Inventario {

    // Arreglos
    private ArrayList<String> nombres = new ArrayList<>();
    private double[] precios = new double[0];
    private HashMap<String, Integer> stock = new HashMap<>();
    private double totalCompras = 0.0;

    // Metodo para agregar un nuevo producto al inventario
    public void addProduct(String nombre, double precio, int cantidad){
        if (nombres.contains(nombre)){
            return;
        }
        nombres.add(nombre);
        precios = expandPrecios(precios, precio);
        stock.put(nombre, cantidad);
    }

    // Metodo privado que expande el arreglo de precios para agregar uno nuevo
    private double[] expandPrecios(double[] oldArray, double nuevoPrecio) {
        double[] nuevoArray = new double[oldArray.length + 1];
        for (int i = 0; i < oldArray.length; i++) {
            nuevoArray[i] = oldArray[i];
        }
        nuevoArray[oldArray.length] = nuevoPrecio;
        return nuevoArray;
    }

    // Devuelve el índice del nombre en la lista, o -1 si no existe
    public int indexNombre(String nombre){
        return nombres.indexOf(nombre);
    }

    // Métodos getter para acceder a los atributos privados
    public ArrayList<String> getNombres(){return  nombres;}
    public double [] getPrecios(){return precios;}
    public HashMap<String, Integer> getStock(){return stock;}

    // Suma el monto de una compra al total acumulado
    public double getTotalCompras(){return totalCompras;}
    public void sumarCompra(double monto){totalCompras += monto;}

}
