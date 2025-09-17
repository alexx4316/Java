import javax.swing.*;

public class FrutaIU {
    Frutos frutos = new Frutos();
    public void menu(){
        while (true) {
            String opt = JOptionPane.showInputDialog(null,
                    """
                            Menu principal:
                            1. Agregar Productos
                            2. Listar Productos
                            3. Buscar Producto por id
                            4. Buscar Producto por nombre
                            5. Actualizar Producto (Por id)
                            6. Eliminar Producto (Por id)
                            0. Salir de la aplicacion
                            """,
                    "Inventario", JOptionPane.QUESTION_MESSAGE);
            if (opt == null){
                continue;
            };
            switch (opt.trim()) {
                case "1":
                    String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre de la fruta: ");
                    double pesokg = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el peso de la furta en kg: "));
                    String color = JOptionPane.showInputDialog(null, "Ingrese el color de su fruta: ");
                    double precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el precio de la fruta: "));
                    String origen = JOptionPane.showInputDialog(null, "Ingrese el origen de su fruta: ");
                    boolean esOrganica = Boolean.parseBoolean(JOptionPane.showInputDialog(null ,"Su fruta es organica? (True/False): "));
                    frutos.anadirFruta(nombre,pesokg,color,precio,origen,esOrganica);
                    break;
                case "2":
                    frutos.listarFrutas();
                    break;
                case "3":
                    int idSearch = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el id de la fruta que desea busar: "));
                    Fruta f = frutos.buscarPorId(idSearch);
                    JOptionPane.showMessageDialog(null, f != null ? f : "No encontrado");
                    break;
                case "4":
                    String nombref = JOptionPane.showInputDialog(null ,"Ingrese el nombre de la fruta que quiere buscar: ");
                    frutos.buscarPorNombre(nombref);
                    break;
                case "5":
                    int idActua = Integer.parseInt(JOptionPane.showInputDialog("Ingrese el id de la fruta que quiere actualizar: "));
                    String nuevoNombre = JOptionPane.showInputDialog(null,"Nuevo nombre: ");
                    double nuevoPeso = Double.parseDouble(JOptionPane.showInputDialog(null,"Nuevo peso: "));
                    String nuevoColor = JOptionPane.showInputDialog(null, "Nuevo color: ");
                    double nuevoPrecio = Double.parseDouble(JOptionPane.showInputDialog(null,"Nuevo precio: "));
                    String nuevoOrigen = JOptionPane.showInputDialog(null,"Nuevo origen: ");
                    boolean nuevoOrganico = Boolean.parseBoolean(JOptionPane.showInputDialog(null, "Nuevo organico: "));
                    if (!frutos.actualizarFruta(idActua, nuevoNombre, nuevoPeso, nuevoColor, nuevoPrecio, nuevoOrigen, nuevoOrganico)){
                        JOptionPane.showMessageDialog(null, "Fruta no enccontrada");
                    }
                    break;
                case "6":
                    String input = JOptionPane.showInputDialog(null, "Ingrese el id de la fruta a eliminar: ");
                    try {
                        int idElm = Integer.parseInt(input.trim());
                        if (!frutos.eliminarFruta(idElm)){
                            JOptionPane.showMessageDialog(null, "Fruta no encontrada");
                        }
                    } catch (NumberFormatException e){
                        JOptionPane.showMessageDialog(null, "ID invalido, Debe ser un numero");
                    }
                    break;
                case "0":
                    System.out.println("Saliendo del programa");
                    System.exit(0);
            }
        }
    }
}
