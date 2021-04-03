package ru.itmo.wp.model.service;

import ru.itmo.wp.model.domain.article.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.repository.ArticleRepository;
import ru.itmo.wp.model.repository.impl.ArticleRepositoryImpl;

import java.util.List;

public class ArticleService {
    private final ArticleRepository articleRepository = new ArticleRepositoryImpl();
    private final int MAX_TITLE_LENGTH = 100;
    private final int MAX_TEXT_LENGTH = 500;


    public void save(Article article) {
        articleRepository.save(article);
    }

    public List<Article> findAll() {
        return articleRepository.findAll();
    }

    public Article find(long id) {
        return articleRepository.find(id);
    }

    public void validateArticleData(Article article) throws ValidationException {
        if (article.getTitle().isEmpty()) {
            throw new ValidationException("Empty input in title");
        }
        if (article.getText().isEmpty()) {
            throw new ValidationException("Empty input in text");
        }
        if (article.getText().length() > MAX_TEXT_LENGTH) {
            throw new ValidationException("Text of Article have to be less than " + MAX_TEXT_LENGTH + " characters");
        }
        if (article.getTitle().length() > MAX_TITLE_LENGTH) {
            throw new ValidationException("Title of Article have to be less than " + MAX_TITLE_LENGTH + " characters");
        }
    }

    public Article changeHiddenById(long articleId, boolean newHidden) {
        return articleRepository.changeHiddenById(articleId, newHidden);
    }

    public List<Article> findByUSerId(long userId) {
        return articleRepository.findByUserId(userId);
    }
}
