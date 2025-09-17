public class Main {
    public static void main(String[] args) {
        // Importamos las clases
        Inventario inventario = new Inventario();
        InventarioIU ui = new InventarioIU(inventario);
        ui.mostrarMenu();
    }
}