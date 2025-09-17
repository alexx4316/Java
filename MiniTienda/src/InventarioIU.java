import javax.swing.*;
import java.util.Locale;
public class InventarioIU {
    private Inventario inventario;

    public InventarioIU(Inventario inventario){
        this.inventario = inventario;
    }

    // Metodo para mostrar el menu
    public void mostrarMenu(){
        while(true){
            String opt = JOptionPane.showInputDialog(null,
                    """
                            Menu principal:
                            1. Agregar Productos
                            2. Listar inventario
                            3. Comprar producto
                            4. Mostrar estadisticas
                            5. Buscar producto
                            6. Salir (ticket final)
                            """,
                    "Inventario", JOptionPane.QUESTION_MESSAGE);

            if (opt == null) return;

            switch (opt.trim()){
                case "1": opcionAgregarProducto(); break;
                case "2": listarInventario(); break;
                case "3": comprarProducto(); break;
                case "4": mostrarEstadistica(); break;
                case "5": buscarProducto(); break;
                case "6": mostrarTicketYSalir(); break;
                default: JOptionPane.showMessageDialog(null, "Opcion invalida.");
            }
        }
        }

    // Opción 1: Agrega un nuevo producto al inventario
    private void opcionAgregarProducto(){
        try {
            String nombre = JOptionPane.showInputDialog("Nombre del producto: ");
            if (nombre == null || nombre.isEmpty()) return;

            double precio = Double.parseDouble(JOptionPane.showInputDialog("Precio: "));
            int stock = Integer.parseInt(JOptionPane.showInputDialog("Stock: "));

            inventario.addProduct(nombre,precio,stock);
            JOptionPane.showMessageDialog(null, "Producto agregado correctamente.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Opción 2: Muestra todos los productos del inventari
    private void listarInventario(){
        StringBuilder sb = new StringBuilder("Inventario:\n");
        for (int i = 0; i < inventario.getNombres().size(); i++) {
            String nom = inventario.getNombres().get(i);
            double pr = inventario.getPrecios()[i];
            int st = inventario.getStock().get(nom);
            sb.append(String.format("%d) %s - $%.2f - Stock: %d\n", i+1, nom,pr,st));
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // Opción 3: Permite comprar un producto del inventario
    private void comprarProducto(){
        try {
            String nombre = JOptionPane.showInputDialog("Producto a comprar:");
            int idx = inventario.indexNombre(nombre);
            if (idx == -1){
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
                return;
            }
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Cantidad: "));
            if (!inventario.getStock().containsKey(nombre)){
                JOptionPane.showMessageDialog(null, "Producto no encontrado en el stock");
                return;
            }
            int stockActual = inventario.getStock().get(nombre);

            if (cantidad > stockActual){
                JOptionPane.showMessageDialog(null,"Stock insuficiente.");
                return;
            }
            double subtotal = inventario.getPrecios()[idx] * cantidad;
            inventario.getStock().put(nombre, stockActual - cantidad);
            inventario.sumarCompra(subtotal);

            JOptionPane.showMessageDialog(null, String.format(Locale.US, "Compra realizada: %s x%d = $%.2f", nombre, cantidad, subtotal));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    // Opción 4: Muestra estadísticas de precios (más barato y más caro)
    private void mostrarEstadistica(){
        if (inventario.getNombres().isEmpty()){
            JOptionPane.showMessageDialog(null,"No hay productos.");
            return;
        }
        int idxMin = 0, idxMax = 0;
        double [] precios = inventario.getPrecios();
        for (int i = 0; i < precios.length; i++) {
            if (precios[i] < precios[idxMin]) idxMin = i;
            if (precios[i] > precios[idxMax]) idxMax = i;
        }
        JOptionPane.showMessageDialog(null, String.format(Locale.US,
                "Mas barato: %s ($%.2f)\nMas caro: %s ($%.2f)",
                inventario.getNombres().get(idxMin), precios[idxMin],
                inventario.getNombres().get(idxMax), precios[idxMax]));
    }

    // Opción 5: Busca productos por nombre (subcadena)
    private void buscarProducto(){
        String termino = JOptionPane.showInputDialog("Buscar producto: ");
        if (termino == null || termino.isEmpty()) return;

        StringBuilder sb = new StringBuilder("Resultado:\n");
        for (int i = 0; i <inventario.getNombres().size() ; i++) {

            String nom  = inventario.getNombres().get(i);
            if (nom.toLowerCase().contains(termino.toLowerCase())){
                sb.append(nom).append("- $").append(inventario.getPrecios()[i])
                        .append("- Stock: ").append(inventario.getStock().get(nom)).append("\n");
            }
        }
        JOptionPane.showMessageDialog(null, sb.length() > 11 ? sb.toString() : "No se encontro nada");
    }

    // Opción 6: Muestra el total acumulado de compras y cierra el programa
    private void mostrarTicketYSalir(){
        JOptionPane.showMessageDialog(null, String.format(Locale.US,
                "Tatal acumulado: $%.2f\nGracias por su compra. ", inventario.getTotalCompras()));
        System.exit(0);
    }
}
