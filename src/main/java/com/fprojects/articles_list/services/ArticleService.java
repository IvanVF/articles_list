package com.fprojects.articles_list.services;

import com.fprojects.articles_list.entities.ArticleEntity;
import com.fprojects.articles_list.repositories.ArticleRepository;
import liquibase.pro.packaged.D;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    /**
     * Get all articles
     */
    public List<ArticleEntity> getAllArticles() {
        return articleRepository.findAll();
    }

    /**
     * Create new article
     */
    public ArticleEntity createArticle(ArticleEntity article) {
        article.setCreatedAt(new Date());
        return articleRepository.save(article);
    }

}
