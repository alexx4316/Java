package main.java.com.tienda.ui;

import main.java.com.tienda.Interface.ArticleInterface;
import main.java.com.tienda.model.Article;
import main.java.com.tienda.repository.ArticleRepository;
import main.java.com.tienda.service.ArticleService;

public class MainUI {
    public static void main(String[] args) {
        // Importamos las clases
        ArticleInterface articleRepository = new ArticleRepository();
        ArticleService service = new ArticleService(articleRepository);
        InventarioIU ui = new InventarioIU(service);
        ui.mostrarMenu();
    }
}