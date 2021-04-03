package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.article.Article;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class ArticlePage extends Page {
    private final ArticleService articleService = new ArticleService();

    public void saveNewArticle(HttpServletRequest request) throws ValidationException {
        User user = validateAuthorizedUser();
        Article article = new Article();
        article.setUserId(user.getId());
        article.setTitle(request.getParameter("titleArticle"));
        article.setText(request.getParameter("textArticle"));
        article.setHidden(false);
        articleService.validateArticleData(article);
        articleService.save(article);
        throw new RedirectException("/index");
    }

    @Json
    public void findAll(Map<String, Object> view) {
        view.put("articles", articleService.findAll());
    }
}
