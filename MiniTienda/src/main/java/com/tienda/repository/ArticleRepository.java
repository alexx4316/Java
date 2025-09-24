package main.java.com.tienda.repository;

import main.java.com.tienda.Interface.ArticleInterface;
import main.java.com.tienda.model.Article;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository implements ArticleInterface {

    // Creamos los arrays y las listas que vamos a usar
    private final List<Article> lista = new ArrayList<>();
    private final HashMap<String, Article> mapa = new HashMap<>();

    // Creamos lo metodos del CRUD

    //Crear o guardar
    @Override
    public void save(Article article){
        lista.add(article);
        mapa.put(article.getCode(), article);
    }

    // Encontrar un articulo por su codigo
    @Override
    public Article findByCode(String code){
        return mapa.get(code);
    }

    // Listar los Articulos
    @Override
    public List<Article> findAll(){
        return  new ArrayList<>(lista);
    }

    // Actualizar datos de un Articulo existente
    @Override
    public boolean update(Article article){
        if (!mapa.containsKey(article.getCode())){
            return false;
        }
        // Remplazamos los datos viejos con los nuevos en la lista y el mapa
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getCode().equalsIgnoreCase(article.getCode())){
                lista.set(i,article);
                break;
            }
        }
        mapa.put(article.getCode(), article);
        return true;
    }

    // Eliminar Articulo por su codigo
    @Override
    public boolean deleteByCode(String code){
        Article rem = mapa.remove(code);
        return rem != null && lista.remove(rem);
    }
}
