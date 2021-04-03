package ru.itmo.wp.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Arrays;

public class StaticServlet extends HttpServlet {
    public static final String ROOT_STATIC = "/home/salavat/WEB/wp3/src/main/webapp/static/";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        String[] files = uri.split("\\+");
        response.setContentType(getContentTypeFromName(files[0]));
        for (String fileName : files) {
            File file = new File(ROOT_STATIC + fileName);
            if (file.isFile()) {
                sendToOutput(response, file);
            } else {
                file = new File(getServletContext().getRealPath("/static/" + fileName));
                if (file.isFile()) {
                    sendToOutput(response, file);
                } else {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                }
            }
        }
        }

        private void sendToOutput(HttpServletResponse response,File file) throws IOException {
            OutputStream outputStream = response.getOutputStream();
            Files.copy(file.toPath(), outputStream);
            outputStream.flush();
        }

        private String getContentTypeFromName (String name){
            name = name.toLowerCase();

            if (name.endsWith(".png")) {
                return "image/png";
            }

            if (name.endsWith(".jpg")) {
                return "image/jpeg";
            }

            if (name.endsWith(".html")) {
                return "text/html";
            }

            if (name.endsWith(".css")) {
                return "text/css";
            }

            if (name.endsWith(".js")) {
                return "application/javascript";
            }

            throw new IllegalArgumentException("Can't find content type for '" + name + "'.");
        }
    }
