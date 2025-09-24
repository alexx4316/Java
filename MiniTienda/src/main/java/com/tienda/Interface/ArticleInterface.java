package main.java.com.tienda.Interface;

import main.java.com.tienda.model.Article;

import java.util.List;

public interface ArticleInterface {
    void save(Article article);
    Article findByCode(String code);
    List<Article> findAll();
    boolean update(Article article);
    boolean deleteByCode(String code);
}
