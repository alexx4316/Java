import javax.swing.*;
import java.util.ArrayList;
import java.awt.Dimension;

public class Frutos {
    private ArrayList<Fruta> frutas = new ArrayList<>();
    private int nextId = 1;

    // Agregar producto
    public void anadirFruta(String nombre, double pesokg, String color, double precio, String origen, boolean esOrganica ){
        Fruta fruta = new Fruta(nextId++, nombre, pesokg, color, precio, origen, esOrganica);
        frutas.add(fruta);
        JOptionPane.showMessageDialog(null, "Fruta creada con exito" + fruta);
    }
    // Listar inventario
    public void listarFrutas() {
        if (frutas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay frutas registradas");
        } else {
            StringBuilder sb = new StringBuilder();
            frutas.forEach(fruta -> sb.append(fruta.toString()).append("\n\n"));

            JTextArea areaTexto = new JTextArea(sb.toString());
            areaTexto.setEditable(false);
            areaTexto.setLineWrap(true);
            areaTexto.setWrapStyleWord(true);

            JScrollPane scroll = new JScrollPane(areaTexto);
            scroll.setPreferredSize(new Dimension(400, 300));

            JOptionPane.showMessageDialog(null, scroll, "Listado de Frutas", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Buscar por id
    public Fruta buscarPorId(int id){
        for (Fruta f : frutas){
            if (f.getId() == id) return f;
        }
        return null;
    }
    // Buscar por nombre
    public void buscarPorNombre(String nombre){
        for (Fruta f : frutas){
            if (f.getNombre().toLowerCase().contains(nombre.toLowerCase())){
                JOptionPane.showMessageDialog(null, f);
            }
        }
    }
    // Actualizar fruta
    public boolean actualizarFruta(int id, String nombre, double pesokg, String color, double precio, String origen, boolean esOrganica){
        Fruta fruta = buscarPorId(id);
        if (fruta != null){
            fruta.setNombre(nombre);
            fruta.setPesokg(pesokg);
            fruta.setColor(color);
            fruta.setPrecio(precio);
            fruta.setOrigen(origen);
            fruta.setEsOrganica(esOrganica);
            JOptionPane.showMessageDialog(null, "Fruta actualizada" + fruta);
            return true;
        }
        return false;
    }
    public boolean eliminarFruta(int id){
        Fruta fruta = buscarPorId(id);
        if (fruta != null){
            frutas.remove(fruta);
            JOptionPane.showMessageDialog(null, "Fruta eliminada" + fruta);
            return true;
        }
        return false;
    }
}