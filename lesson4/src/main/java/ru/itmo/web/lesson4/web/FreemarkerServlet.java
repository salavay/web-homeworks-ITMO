package ru.itmo.web.lesson4.web;

import freemarker.template.*;
import ru.itmo.web.lesson4.util.DataUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerServlet extends HttpServlet {
    private static final String UTF_8 = StandardCharsets.UTF_8.name();
    private final Configuration cfg = new Configuration(Configuration.VERSION_2_3_30);

    @Override
    public void init() throws ServletException {
        super.init();

        File dir = new File(getServletContext().getRealPath("."), "../../src/main/webapp/WEB-INF/templates");
        try {
            cfg.setDirectoryForTemplateLoading(dir);
        } catch (IOException e) {
            throw new ServletException("Unable to set template directory [dir=" + dir + "].", e);
        }

        cfg.setDefaultEncoding(UTF_8);
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding(UTF_8);
        response.setCharacterEncoding(UTF_8);

        Template template;
        try {
            String requestURI = request.getRequestURI();
            if (requestURI.equals("/")) {
                response.sendRedirect("/index");
                return;
            }
            template = cfg.getTemplate(URLDecoder.decode(requestURI, UTF_8) + ".ftlh");
        } catch (Exception e) {
            template = cfg.getTemplate( "404.ftlh");
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }

        Map<String, Object> data = getData(request);

        response.setContentType("text/html");
        try {
            template.process(data, response.getWriter());
        } catch (TemplateException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private Map<String, Object> getData(HttpServletRequest request) {
        Map<String, Object> data = new HashMap<>();

        data.put("uri", request.getRequestURI());

        for (Map.Entry<String, String[]> e : request.getParameterMap().entrySet()) {
            if (e.getValue() != null && e.getValue().length == 1) {
                if (e.getKey().endsWith("_id")) {
                    try {
                        data.put(e.getKey(), Long.parseLong(e.getValue()[0]));
                    } catch (NumberFormatException ignored) {
                        data.put(e.getKey(), e.getValue()[0]);
                    }
                } else {
                    data.put(e.getKey(), e.getValue()[0]);
                }
            }
        }

        DataUtil.addData(request, data);
        return data;
    }
}
