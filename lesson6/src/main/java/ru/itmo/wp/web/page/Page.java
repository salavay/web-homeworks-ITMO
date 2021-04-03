package ru.itmo.wp.web.page;

import com.google.common.base.Strings;
import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public abstract class Page {
    private final UserService userService = new UserService();
    private HttpServletRequest request;

    void before(HttpServletRequest request, Map<String, Object> view) {
        this.request = request;
        putUser(request, view);
        putMessage(request, view);
    }

    protected void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("userCount", userService.findCount());
    }

    protected void action() {
    }

    private void putUser(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            view.put("user", user);
        }
    }

    private void putMessage(HttpServletRequest request, Map<String, Object> view) {
        String message = (String) request.getSession().getAttribute("headerMessage");
        if (!Strings.isNullOrEmpty(message)) {
            view.put("headerMessage", message);
            request.getSession().removeAttribute("headerMessage");
        }
    }

    protected void setMessage(String message) {
        request.getSession().setAttribute("headerMessage", message);
    }

    protected void setUser(User user) {
        request.getSession().setAttribute("user", user);
    }

    protected User getUser() {
        return (User) request.getSession().getAttribute("user");
    }
}
