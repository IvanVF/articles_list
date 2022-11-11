package com.fprojects.articles_list.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Entity of article
 */
@Entity
@Table(name = "articles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleEntity extends BaseEntity {

    @Column(name = "title")
    @Length(min = 3, max = 100, message = "Title should be at least 3 and less then 100 symbols")
    private String title;

    @Column(name = "author")
    @Length(min = 3, max = 100, message = "Author name should be at least 3 and less then 100 symbols")
    private String author;

    @Column(name = "content")
    @Length(min = 3, max = 100, message = "Content should be at least 3 and less then 100 symbols")
    private String content;
}
