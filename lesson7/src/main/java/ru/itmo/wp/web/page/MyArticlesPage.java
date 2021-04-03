package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.ArticleService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class MyArticlesPage extends Page {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        User user = validateAuthorizedUser();
        view.put("articles", articleService.findByUSerId(user.getId()));
    }

    @Json
    public void changeHiddenById(HttpServletRequest request, Map<String, Object> view) {
        User user = validateAuthorizedUser();
        long articleId = Long.parseLong(request.getParameter("id"));
        boolean newHidden = Boolean.parseBoolean(request.getParameter("hidden"));
        if (user.getId() != (articleService.find(articleId)).getUserId()) {
            setMessage("It's not your article");
            throw new RedirectException("/index");
        }
        view.put("changedArticle", articleService.changeHiddenById(articleId, newHidden));
    }
}
