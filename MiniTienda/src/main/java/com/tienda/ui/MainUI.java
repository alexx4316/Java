package main.java.com.tienda.ui;

import main.java.com.tienda.Interface.RepositoryInterface;
import main.java.com.tienda.repository.ProductRepository;
import main.java.com.tienda.service.InventoryService;

public class MainUI {
    public static void main(String[] args) {
        // Importamos las clases
        RepositoryInterface articleRepository = new ProductRepository();
        InventoryService service = new InventoryService(articleRepository);
        InventarioIU ui = new InventarioIU(service);
        ui.mostrarMenu();
    }
}