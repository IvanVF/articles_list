package com.fprojects.articles_list.repositories;

import com.fprojects.articles_list.entities.ArticleEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ArticleRepository  extends JpaRepository<ArticleEntity, Long> {
    /**
     * Get all articles with pagination
     */
    @Query(value = "SELECT * FROM articles", nativeQuery = true)
    List<ArticleEntity> list(Pageable pageable);

    List<ArticleEntity> findAllByCreatedAtBetween(Date dateStart, Date dateEnd, Pageable pageable);
    Integer countByCreatedAtBetween(Date dateStart, Date dateEnd);
}
