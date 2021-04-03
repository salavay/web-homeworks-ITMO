package ru.itmo.wp.web.page;

import ru.itmo.wp.model.service.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class IndexPage extends Page {
    private final ArticleService articleService = new ArticleService();

    @Override
    protected void before(HttpServletRequest request, Map<String, Object> view) {
        super.before(request, view);
        view.put("articles", articleService.findAll());
    }
}
