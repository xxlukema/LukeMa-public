package com.broadsoft.cpbx.e911.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "VersionServlet", urlPatterns = { "/ver" })
public class VersionServlet
    extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private final String MENIFEST = "/META-INF/MANIFEST.MF";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
        response.setDateHeader("Expires", 0); //prevents caching at the proxy server
        PrintWriter out = response.getWriter();
        try {
            Properties prop = new Properties();
            prop.load(getServletContext().getResourceAsStream(MENIFEST));

            Set<Object> keys = prop.keySet();
            for (Object obj : keys) {
                if (obj instanceof String) {
                    String key = (String) obj;
                    String value = prop.getProperty(key);

                    if (value == null || (value = value.trim()).length() == 0 || value.contains("{") || value.contains("SNAPSHOT")) {
                        continue;
                    }

                    out.println(key + ": " + value);
                    out.println("<br>");
                }
            }
        } finally {
            out.close();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }
}
