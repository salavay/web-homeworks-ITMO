package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.domain.event.EventType;
import ru.itmo.wp.model.service.EventService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@SuppressWarnings({"unused", "RedundantSuppression"})
public class LogoutPage extends Page{
    private final EventService eventService = new EventService();

    private void action(HttpServletRequest request, Map<String, Object> view) {
        User user = (User) request.getSession().getAttribute("user");
        request.getSession().removeAttribute("user");
        if (user != null) {
            eventService.save(user, EventType.LOGOUT);
        }
        setMessage("Good bye. Hope to see you soon!");
        throw new RedirectException("/index");
    }
}
