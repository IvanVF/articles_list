package com.fprojects.articles_list.controllers;

import com.fprojects.articles_list.entities.ArticleEntity;
import com.fprojects.articles_list.services.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller for article handling
 */
@RestController
@RequestMapping("/articles")
public class ArticleController {

    private final ArticleService articleService;

    @Autowired
    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    /**
     * Get all articles
     */
    @GetMapping
    public List<ArticleEntity> getAllArticles() {
        return articleService.getAllArticles();
    }

    /**
     * Get last articles
     */
    @GetMapping("/get_last")
    public List<ArticleEntity> getLastArticles() {
        return articleService.getAllArticles();
    }

    /**
     * Create new article
     */
    @PostMapping
    public ArticleEntity createArticle(@Valid @RequestBody ArticleEntity article) {
        return articleService.createArticle(article);
    }
}
