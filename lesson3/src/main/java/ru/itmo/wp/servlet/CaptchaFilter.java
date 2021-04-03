package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Base64;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request.getMethod().equals("GET")) {
            HttpSession session = request.getSession();
            Object sessionCaptcha = session.getAttribute("captcha");
            if (sessionCaptcha == null) {
                generateAndWriteCaptcha(request, response, true);
            } else if (sessionCaptcha.equals("Passed")) {
                chain.doFilter(request, response);
            } else {
                tryingToPass(request, response, chain);
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    private void generateAndWriteCaptcha(HttpServletRequest request, HttpServletResponse response, boolean generateNew) throws IOException {
        String captchaNumber = (String) request.getSession().getAttribute("captcha");
        if (generateNew) {
            int r = 999, l = 100;
            captchaNumber = Integer.toString(l + (int) (Math.random() * (r - l)));
        }
        byte[] captcha = ImageUtils.toPng(captchaNumber);
        String encoded = Base64.getEncoder().encodeToString(captcha);
        request.getSession().setAttribute("captcha", captchaNumber);
        response.getWriter().print(makeHtml(encoded));
        response.getWriter().flush();
    }

    private void tryingToPass(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String sessionCaptcha = (String) session.getAttribute("captcha");
        String userInputCaptcha = request.getParameter("userInput");
        if (userInputCaptcha == null) {
            generateAndWriteCaptcha(request, response, false);
            return;
        }
        if (sessionCaptcha.equals(userInputCaptcha)) {
            session.setAttribute("captcha", "Passed");
            chain.doFilter(request, response);
        } else {
            generateAndWriteCaptcha(request, response, true);
        }
    }

    private String makeHtml(String image) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "  <head>\n" +
                "  <title>" + "CAPTCHA </title>\n" +
                "  </head>\n" +
                "  <body>\n" +
                "    <img style='display:block; width:100px;height:100px;' id='base64image'                 \n" +
                "       src='data:image/jpeg;base64, " + image + " ' />\n" +
                "    <form>\n" +
                "            <label> Enter captcha:</label>\n" +
                "            <input name=\"userInput\" id=\"userInput\" type=\"text\">\n" +
                "    </form>" +
                "  </body>\n" +
                "</html>";
    }
}
