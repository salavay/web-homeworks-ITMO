package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.annotation.Json;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class UsersPage extends Page {
    private final UserService userService = new UserService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findAll(Map<String, Object> view) {
        view.put("users", userService.findAll());
    }

    private void findUser(HttpServletRequest request, Map<String, Object> view) {
        view.put("user", userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    private void findLoginById(HttpServletRequest request, Map<String, Object> view) {
        view.put("userWithLoginById", userService.find(Long.parseLong(request.getParameter("userId"))));
    }

    @Json
    public void changeAdminById(HttpServletRequest request, Map<String, Object> view) {
        if (!validateAuthorizedUser().isAdmin()) {
            setMessage("You aren't admin");
            throw new RedirectException("/users");
        }
        long id = Long.parseLong(request.getParameter("userId"));
        String command = request.getParameter("isAdmin");
        boolean newIsAdmin = !Boolean.parseBoolean(command);
        //newIsAdmin = command.equals("Enable");
        User changedUser = userService.changeAdminById(id, newIsAdmin);
        view.put("changedUser", changedUser);
        if (validateAuthorizedUser().getId() == id) {
            throw new RedirectException("/users");
        }
    }
}
