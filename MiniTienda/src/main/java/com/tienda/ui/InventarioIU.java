package main.java.com.tienda.ui;
import main.java.com.tienda.model.Product;
import main.java.com.tienda.service.InventoryService;
import main.java.com.tienda.util.DialogUtil;
import main.java.com.tienda.validation.InputValidator;
import main.java.com.tienda.validation.ValidationException;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
public class InventarioIU {
    private final InventoryService inventoryService;
    private double totalSales = 0.0;

    public InventarioIU(InventoryService inventoryService){
        this.inventoryService = inventoryService;
    }

    // Metodo para mostrar el menu
    public void mostrarMenu(){
        String opt;
        do {
            opt = DialogUtil.prompt(
                            "Menu principal:\n" +
                            "1. Agregar Productos\n" +
                            "2. Listar Productos\n" +
                            "3. Comprar Productos\n" +
                            "4. Mostrar Estadisticas\n" +
                            "5. Buscar Producto por id\n" +
                            "6. Salir (ticket final)\n"
            );
            if (opt == null) return;

            try {
                switch (opt.trim()){
                    case "1": opcionAgregarProducto(); break;
                    case "2": listarInventario(); break;
                    case "3": comprarProducto(); break;
                    case "4": mostrarEstadistica(); break;
                    case "5": buscarProducto(); break;
                    case "6": mostrarTicketYSalir(); break;
                    default: DialogUtil.error("Opcion invalida.");
                }
            } catch (ValidationException ve){
                DialogUtil.error(ve.getMessage());
            }
        } while (!"6".equals(opt.trim()));
    }

    // Opción 1: Agrega un nuevo producto al articleRepository
    private void opcionAgregarProducto(){
        String name = InputValidator.requireNonEmpty(DialogUtil.prompt("Enter Name: "), "name");
        BigDecimal price = new BigDecimal(InputValidator.parsePositiveDouble(DialogUtil.prompt("Enter Price"), "price"));
        int stock = InputValidator.parsePositiveInt(DialogUtil.prompt("Enter Stock: "), "Stock");
        String type = InputValidator.requireNonEmpty(DialogUtil.prompt("Enter Type"), "type");

        try {
            inventoryService.addProduct(name,price,stock,type);
            DialogUtil.info("Product added correctly.");
        } catch (ValidationException e) {
            DialogUtil.info(e.getMessage());
        }
    }

    // Opción 2: Muestra todos los productos del inventari
    private void listarInventario(){
        List<Product> all = inventoryService.findAll();
        if (all.isEmpty()){
            DialogUtil.info("Empty inventory.");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder("===Product list===\n");
        all.forEach(p -> stringBuilder.append(p).append("\n"));
        DialogUtil.info(stringBuilder.toString());
    }

    // Opción 3: Permite comprar un producto del articleRepository
    private void comprarProducto(){
        int id = InputValidator.parsePositiveInt(DialogUtil.prompt("Product id"), "id");
        try {
            Product product = inventoryService.findById(id);
            int qty = InputValidator.parsePositiveInt(DialogUtil.prompt("cuantity: "), "cuantity");

            if (qty > product.getStock()){
                DialogUtil.error("Stock insuficiente. Disponibles: " + product.getStock());
                return;
            }

            inventoryService.updateProduct(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getStock() - qty,
                    product.getType()
            );

            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(qty));
            totalSales += subtotal.doubleValue();

            DialogUtil.info(String.format(Locale.US,
                    "buy: %s x%d = $%.2f",
                    product.getName(), qty, subtotal));
        } catch (SQLException e) {
            DialogUtil.error("Error when purchasing");
        }
    }

    // Opción 4: Muestra estadísticas de precios (más barato y más caro)
    private void mostrarEstadistica(){
        List<Product> all = inventoryService.findAll();
        if (all.isEmpty()){
            DialogUtil.info("No data for statistics: ");
            return;
        }
        Product min = all.get(0), max = all.get(0);
        for (Product p : all){
            if (p.getPrice().compareTo(min.getPrice()) < 0) min = p;
            if (p.getPrice().compareTo(max.getPrice()) > 0) min = p;
        }
        DialogUtil.info(String.format(Locale.US,
                "More cheap: %s ($%.2f)\nMore expensive: %s ($%.2f)",
                min.getName(), min.getPrice(),
                max.getName(), max.getPrice()));
    }

    // Opción 5: Busca productos por nombre (subcadena)
    private void buscarProducto(){
        int id = InputValidator.parsePositiveInt(DialogUtil.prompt("Search by id"), "id");
        try {
            Product p = inventoryService.findById(id);
            if (p == null){
                DialogUtil.info("Not found");
            } else {
                DialogUtil.info(String.format(
                        "ID: %d | Name: %s | Price: $%.2f | Stock: %d | Type: %s",
                        p.getId(),p.getName(),p.getPrice(),p.getStock(),p.getType()
                ));
            }
        } catch (Exception e) {
            DialogUtil.error("Error searching product: " + e.getMessage());
        }
    }

    // Opción 6: Muestra el total acumulado de compras y cierra el programa
    private void mostrarTicketYSalir(){
        DialogUtil.info(String.format(Locale.US,
                "Total sales: $%.2f \nThank you for your purchase.",
                totalSales));
        System.exit(0);
    }
}
