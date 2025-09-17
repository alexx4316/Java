import java.util.*;
import java.util.List;

// Info plato de menu
class Plato {
    private String nombre;
    private double precio;

    public Plato(String nombre, double precio){
        this.nombre = nombre;
        this.precio = precio;
    }

    public String getNombre() {return nombre;}
    public double getPrecio(){return precio;}

    @Override
    public String toString(){
        return nombre + " - $" + precio;
    }
}

// Info clientes
class Cliente {
    private String nombre;
    private Orden orden;

    public Cliente(String nombre){
        this.nombre = nombre;
    }

    public void hacerOrden(Orden orden){
        this.orden = orden;
    }

    public Orden getOrden() {
        return orden;
    }

    public String getNombre(){
        return nombre;
    }
}

class Orden {
    private List<Plato> platos = new ArrayList<>();
    private boolean pagada = false;

    public void agregarPlato(Plato plato){
        platos.add(plato);
    }

    public double calcularTotal(){
        return platos.stream().mapToDouble(Plato :: getPrecio).sum();
    }

    public void marcarPagado(){
        this.pagada = true;
    }

    public boolean estapagada(){
        return pagada;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder("Orden: \n");
        for (Plato p: platos){
            sb.append("- ").append(p.toString()).append("\n");
        }
        sb.append("Total: $").append(calcularTotal());
        sb.append(pagada ? " (✅ Pagada)" : " (❌ Pendiente de pago)");
        return sb.toString();
    }
}

// Flujo del restaurante y los pagos
class Restaurante{
    private List<Plato> menu = new ArrayList<>();
    private List<Cliente> clientes = new ArrayList<>();

    public void agregarPlatoMenu(Plato plato){
        menu.add(plato);
    }

    public void mostrarMenu(){
        System.out.println("📋 Menú");
        for (int i = 0; i < menu.size(); i++) {
            System.out.println((i+1) + ". " + menu.get(i));
        }
    }

    public Plato getPlato(int index) {
        if (index >= 0 && index < menu.size()) {
            return menu.get(index);
        }
        return null;
    }

    public void registrarCliente(Cliente cliente){
        clientes.add(cliente);
    }

    public void cobrarCliente(Cliente cliente){
        if (cliente.getOrden() == null){
            System.out.println(cliente.getNombre() + " no ha hecho ninguna orden");
            return;
        }
        Orden orden = cliente.getOrden();
        double total = orden.calcularTotal();
        System.out.println("💰 El cliente " + cliente.getNombre() + " debe pagar $" + total);

        // Marcar como pagada
        //orden.marcarPagado();
        System.out.println("✅ Pago registrado, buen provecho");
    }

    public void servirOrden(Cliente cliente) {
        if (cliente.getOrden() == null) {
            System.out.println("⚠️ " + cliente.getNombre() + " no tiene ninguna orden.");
            return;
        }
        Orden orden = cliente.getOrden();
        if (!orden.estapagada()) {
            System.out.println("🚫 No se puede servir la orden. Falta el pago.");
        } else {
            System.out.println("🍽️ Sirviendo la orden de " + cliente.getNombre());
            System.out.println(orden);
        }
    }
}

public class SolucionRest {
    public static void main(String[] args) {
        Restaurante restaurante = new Restaurante();
        Scanner scanner = new Scanner(System.in);

        // Creamos un menu
        restaurante.agregarPlatoMenu(new Plato("Arroz con pollo", 15.0));
        restaurante.agregarPlatoMenu(new Plato("Bandeja paisa", 20.0));
        restaurante.agregarPlatoMenu(new Plato("Sopa de verduras", 10.0));

        // Registramos un cliente
        System.out.print("Ingrese el nombre del cliente: ");
        String nombreCliente = scanner.nextLine();
        Cliente client1 = new Cliente(nombreCliente);
        restaurante.registrarCliente(client1);

        // Hacer orden
        Orden orden1 = new Orden();
        int opt;
        do {
            restaurante.mostrarMenu();
            System.out.print("Seleccione un plato del menú (0 para terminar): ");
            opt = scanner.nextInt();
            if (opt > 0 && opt <= 3){
                Plato plato = restaurante.getPlato(opt -1);
                orden1.agregarPlato(plato);
                System.out.println(plato.getNombre() + " agregado a la orden.");
            }
        } while (opt != 0);

        client1.hacerOrden(orden1);

        System.out.println("\nResumen de la orden: ");
        System.out.println(orden1);

        restaurante.cobrarCliente(client1);
        restaurante.servirOrden(client1);
    }
}
