package ru.itmo.wp.model.repository;

import ru.itmo.wp.model.domain.article.Article;

import java.util.List;

public interface ArticleRepository {
    void save(Article article);
    Article find(long id);
    List<Article> findAll();
    Article changeHiddenById(long id, boolean newHidden);
    List<Article> findByUserId(long userId);
}
