package main.java.com.tienda.service;

import main.java.com.tienda.Interface.ArticleInterface;
import main.java.com.tienda.model.Article;
import java.util.List;

public class ArticleService {
    private final ArticleInterface articleRepository;

    public ArticleService(ArticleInterface articleRepository) {
        this.articleRepository = articleRepository;
    }


    public  void addArticle(Article article){
        articleRepository.save(article);
    }

    public Article getArticle(String code){
        return articleRepository.findByCode(code);
    }

    public List<Article> getAll(){
        return articleRepository.findAll();
    }

    public boolean updateArticle(Article article){
        return articleRepository.update(article);
    }

    public boolean removeArticle(String code){
        return articleRepository.deleteByCode(code);
    }

}
