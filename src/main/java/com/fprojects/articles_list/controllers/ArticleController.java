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
     * Get all articles with pagination
     */
    @GetMapping
    public List<ArticleEntity> getAllArticles(@RequestParam(name = "per_page", required = false) Integer perPage,
                                              @RequestParam(name = "page", required = false) Integer page
    ) {
        perPage = perPage == null ? 5 : perPage;
        page = page == null ? 0 : page;
        return articleService.getAllArticles(perPage, page);
    }

    /**
     * Get last week articles with pagination
     * Available only for admin
     */
    @GetMapping("/get_last")
    public List<ArticleEntity> getLastArticles(@RequestParam(name = "per_page", required = false) Integer perPage,
                                               @RequestParam(name = "page", required = false) Integer page) {
        perPage = perPage == null ? 5 : perPage;
        page = page == null ? 0 : page;
        return articleService.getLastWeekArticles(perPage, page);
    }

    /**
     * Get last week articles count
     * Available only for admin
     */
    @GetMapping("/get_last_count")
    public Integer getLastWeekArticlesCount() {
        return articleService.getLastWeekArticlesCount();
    }

    /**
     * Create new article
     */
    @PostMapping
    public ArticleEntity createArticle(@Valid @RequestBody ArticleEntity article) {
        return articleService.createArticle(article);
    }
}
