package com.fprojects.articles_list.services;

import com.fprojects.articles_list.entities.ArticleEntity;
import com.fprojects.articles_list.repositories.ArticleRepository;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

public class ArticleServiceTests {

    @Test
    public void createArticleTest() {
        ArticleEntity newArticle = new ArticleEntity("Title", "Author", "content");
        newArticle.setCreatedAt(new Date());
        ArticleEntity createdArticle = new ArticleEntity("Title", "Author", "content");
        createdArticle.setId(1L);
        createdArticle.setCreatedAt(new Date());

        ArticleRepository articleRepository = mock(ArticleRepository.class);
        when(articleRepository.save(Mockito.any())).thenReturn(createdArticle);

        ArticleService articleService = new ArticleService(articleRepository);

        ArticleEntity responseArticle = articleService.createArticle(newArticle);

        assertEquals(responseArticle.getAuthor(), newArticle.getAuthor());
        assertEquals(responseArticle.getContent(), newArticle.getContent());
        assertEquals(responseArticle.getTitle(), newArticle.getTitle());
        assertEquals(responseArticle.getId(), 1);
    }

    @Test
    public void getAllArticlesTest() {
        ArticleEntity firstArticle = new ArticleEntity("Title", "Author", "content");
        firstArticle.setId(1L);
        firstArticle.setCreatedAt(new Date());
        ArticleEntity secondArticle = new ArticleEntity("Title2", "Author2", "content2");
        secondArticle.setId(2L);
        secondArticle.setCreatedAt(new Date());

        List<ArticleEntity> articles = Arrays.asList(firstArticle, secondArticle);
        ArticleRepository articleRepository = mock(ArticleRepository.class);
        when(articleRepository.list(Mockito.any())).thenReturn(articles);

        ArticleService articleService = new ArticleService(articleRepository);

        List<ArticleEntity> firstPageArticles = articleService.getAllArticles(5, 0);

        assertEquals(firstPageArticles.size(), 2);
    }

    @Test
    public void getLastWeekArticlesTest() {
        ArticleEntity firstArticle = new ArticleEntity("Title", "Author", "content");
        firstArticle.setId(1L);
        firstArticle.setCreatedAt(new Date());
        ArticleEntity secondArticle = new ArticleEntity("Title2", "Author2", "content2");
        secondArticle.setId(2L);
        secondArticle.setCreatedAt(new Date());

        List<ArticleEntity> articles = Arrays.asList(firstArticle, secondArticle);
        ArticleRepository articleRepository = mock(ArticleRepository.class);
        when(articleRepository.findAllByCreatedAtBetweenAndDeletedAtIsNull(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(articles);

        ArticleService articleService = new ArticleService(articleRepository);

        List<ArticleEntity> lastWeekArticles = articleService.getLastWeekArticles(5, 0);

        assertEquals(lastWeekArticles.size(), 2);
    }

    @Test
    public void getLastWeekArticlesCount() {
        ArticleRepository articleRepository = mock(ArticleRepository.class);
        when(articleRepository.countByCreatedAtBetweenAndDeletedAtIsNull(Mockito.any(), Mockito.any())).thenReturn(2);

        ArticleService articleService = new ArticleService(articleRepository);

        Integer articlesCount = articleService.getLastWeekArticlesCount();
        assertEquals(articlesCount, 2);
    }
}
