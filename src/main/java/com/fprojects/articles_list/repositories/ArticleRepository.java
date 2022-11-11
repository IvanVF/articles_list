package com.fprojects.articles_list.repositories;

import com.fprojects.articles_list.entities.ArticleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository  extends JpaRepository<ArticleEntity, Long> {
    List<ArticleEntity> findAll();
}
