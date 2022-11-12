package com.fprojects.articles_list.services;

import com.fprojects.articles_list.entities.ArticleEntity;
import com.fprojects.articles_list.repositories.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
public class ArticleService {

    private ArticleRepository articleRepository;

    @Autowired
    public ArticleService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    private final int MILLISECONDS_IN_WEEK = 604800000;

    /**
     * Get all articles
     */
    public List<ArticleEntity> getAllArticles(Integer perPage, Integer page) {
        Pageable pageable = PageRequest.of(page, perPage);
        return articleRepository.list(pageable);
    }

    /**
     * Get last week articles
     */
    public List<ArticleEntity> getLastWeekArticles(Integer perPage, Integer page) {
        Pageable pageable = PageRequest.of(page, perPage);
        return articleRepository.findAllByCreatedAtBetweenAndDeletedAtIsNull(new Timestamp((Timestamp.from(Instant.now()).getTime() - MILLISECONDS_IN_WEEK)), new Date(), pageable);
    }

    /**
     * Get last week articles count
     */
    public Integer getLastWeekArticlesCount() {
        return articleRepository.countByCreatedAtBetweenAndDeletedAtIsNull(new Timestamp((Timestamp.from(Instant.now()).getTime() - MILLISECONDS_IN_WEEK)), new Date());
    }

    /**
     * Create new article
     */
    public ArticleEntity createArticle(ArticleEntity article) {
        if (article.getCreatedAt() == null) {
            article.setCreatedAt(new Date());
        }

        return articleRepository.save(article);
    }

}
