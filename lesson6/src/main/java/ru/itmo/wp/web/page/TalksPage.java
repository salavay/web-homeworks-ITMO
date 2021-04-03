package ru.itmo.wp.web.page;

import ru.itmo.wp.model.domain.User;
import ru.itmo.wp.model.exception.ValidationException;
import ru.itmo.wp.model.service.TalkService;
import ru.itmo.wp.model.service.UserService;
import ru.itmo.wp.web.exception.RedirectException;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class TalksPage extends Page {
    private final UserService userService = new UserService();
    private final TalkService talkService = new TalkService();

    protected void action(Map<String, Object> view) {
        User user = getUser();
        if (user == null) {
            setMessage("Sorry, you should login to get access for Taks");
            throw new RedirectException("/index");
        }
    }

    private void send(HttpServletRequest request, Map<String, Object> view) throws ValidationException {
        if (getUser() == null) {
            throw new RedirectException("/index");
        }
        String targetUserLogin = request.getParameter("targetUserLogin");
        String message = request.getParameter("message");
        talkService.validateMessage(message);
        talkService.saveByLogin(getUser(), targetUserLogin, message);
        throw new RedirectException("/talks");
    }

    @Override
    public void after(HttpServletRequest request, Map<String, Object> view) {
        view.put("users", userService.findAll());
        User user = getUser();
        if (user != null) {
            view.put("messages", talkService.findPrivateMessages(user.getId()));
        }
        super.after(request, view);
    }
}
