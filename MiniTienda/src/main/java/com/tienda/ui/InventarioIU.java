package main.java.com.tienda.ui;
import main.java.com.tienda.model.Article;
import main.java.com.tienda.model.Food;
import main.java.com.tienda.model.Product;
import main.java.com.tienda.service.ArticleService;
import main.java.com.tienda.util.DialogUtil;
import main.java.com.tienda.validation.InputValidator;
import main.java.com.tienda.validation.ValidationException;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Locale;
public class InventarioIU {
    private final ArticleService articleService;
    private double totalVentas = 0.0;

    public InventarioIU(ArticleService articleService){
        this.articleService = articleService;
    }

    // Metodo para mostrar el menu
    public void mostrarMenu(){
        String opt;
        do {
            opt = DialogUtil.prompt(
                            "Menu principal:\n" +
                            "1. Agregar Productos\n" +
                            "2. Listar articleRepository\n" +
                            "3. Comprar producto\n" +
                            "4. Mostrar estadisticas\n" +
                            "5. Buscar producto\n" +
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
        String type = DialogUtil.prompt("Tipo (F = Food, P = Product): ");
        String name = InputValidator.requireNonEmpty(DialogUtil.prompt("Nombre: "), "Nombre");
        String code = InputValidator.requireNonEmpty(DialogUtil.prompt("Codigo: "), "Codigo");
        double price = InputValidator.parsePositiveDouble(DialogUtil.prompt("Codigo: "), "Codigo");
        int stock = InputValidator.parsePositiveInt(DialogUtil.prompt("Stock: "), "Stock");

        Article article = "F".equalsIgnoreCase(type)
                ? new Food(name, code, price, stock,
                InputValidator.requireNonEmpty(DialogUtil.prompt("Dias para caducar: "), "Dias"))
                : new Product(name, code, price, stock,
                InputValidator.requireNonEmpty(DialogUtil.prompt("Categoria: "), "Categoria"));
        articleService.addArticle(article);
        DialogUtil.info("Producto agregado.");
    }

    // Opción 2: Muestra todos los productos del inventari
    private void listarInventario(){
        List<Article> all = articleService.getAll();
        if (all.isEmpty()){
            DialogUtil.info("Inventario vacio. ");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        all.forEach(a -> stringBuilder.append(a.getDescription()).append("\n"));
        DialogUtil.info(stringBuilder.toString());
    }

    // Opción 3: Permite comprar un producto del articleRepository
    private void comprarProducto(){
        String code = InputValidator.requireNonEmpty(DialogUtil.prompt("Codigo a comprar: "), "Codigo");
        Article article = articleService.getArticle(code);
        if (article == null) {
            DialogUtil.error("No existe ese producto" + code);
            return;
        }
        int qty = InputValidator.parsePositiveInt(DialogUtil.prompt("Cantidad: "), "Cantidad");
        if(qty > article.getStock()){
            DialogUtil.error("Stock insuficiente. Disponibles: " + article.getStock());
            return;
        }
        article.setStock(article.getStock() - qty);
        articleService.updateArticle(article);
        double subTotal = article.getPrice() * qty;
        totalVentas += subTotal;
        DialogUtil.info(String.format(Locale.US, "Comprado: %s x%d = $%.2f", article.getName(), qty, subTotal));
    }

    // Opción 4: Muestra estadísticas de precios (más barato y más caro)
    private void mostrarEstadistica(){
        List<Article> all = articleService.getAll();
        if (all.isEmpty()){
            DialogUtil.info("Sin datos para estadisticas: ");
            return;
        }
        Article min = all.get(0), max = all.get(0);
        for (Article a : all){
            if (a.getPrice() < min.getPrice()) min = a;
            if (a.getPrice() > min.getPrice()) max = a;
        }
        DialogUtil.info(String.format(Locale.US,
                "Mas Barato: %s ($%.2f)\nMas caro: %s ($%.2f)",
                min.getName(), min.getPrice(),
                max.getName(), max.getPrice()));
    }

    // Opción 5: Busca productos por nombre (subcadena)
    private void buscarProducto(){
        String term = InputValidator.requireNonEmpty(DialogUtil.prompt("Buscar (Fragmento)"), "Termino");
        List<Article> all = articleService.getAll();
        StringBuilder stringBuilder = new StringBuilder();
        for (Article a : all){
            if (a.getName().toLowerCase().contains(term.toLowerCase())){
                stringBuilder.append(a.getDescription()).append("\n");
            }
        }
        DialogUtil.info(stringBuilder.length() == 0 ? "No encontrao" : stringBuilder.toString());
    }

    // Opción 6: Muestra el total acumulado de compras y cierra el programa
    private void mostrarTicketYSalir(){
        DialogUtil.info(String.format(Locale.US,
                "Total de ventas: $%.2f\nGracias por su compra.",
                totalVentas));
        System.exit(0);
    }
}
