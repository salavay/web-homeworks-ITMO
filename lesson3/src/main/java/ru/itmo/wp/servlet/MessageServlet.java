package ru.itmo.wp.servlet;

import com.google.gson.Gson;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

public class MessageServlet extends HttpServlet {
    final Chat chat = new Chat();

    private static class Message {
        String user;
        String text;

        public Message(String name, String text) {
            this.user = name;
            this.text = text;
        }
    }

    private static class Chat {
        ArrayList<Message> messages = new ArrayList<>();

        public synchronized void addMessage(Message message) {
            messages.add(message);
        }

        public synchronized ArrayList<Message> getMessages() {
            return messages;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String command = uri.substring(9);
        HttpSession session = request.getSession();
        String userFromSession = (String) session.getAttribute("user");
        String userFromRequest = request.getParameter("user");
        if ("auth".equals(command)) {
            sendObjectToOutput(response, (userFromRequest == null ? "" : userFromRequest));
            if (userFromRequest != null) {
                session.setAttribute("user", userFromRequest);
            }
        } else if ("findAll".equals(command)) {
            sendObjectToOutput(response, chat.getMessages());
        } else if ("add".equals(command)) {
            String textFromRequest = request.getParameter("text");
            chat.addMessage(new Message(userFromSession, textFromRequest));
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    private void sendObjectToOutput(HttpServletResponse response, Object object) throws IOException {
        String json = new Gson().toJson(object);
        response.getWriter().print(json);
        response.getWriter().flush();
        response.setContentType("application/json");
    }
}
