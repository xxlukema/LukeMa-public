package com.learn.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.Set;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "VersionServlet", urlPatterns = { "/ver" })
public class VersionServlet
    extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final org.apache.logging.log4j.Logger LOG4J2 = org.apache.logging.log4j.LogManager.getLogger();
    private static final java.util.logging.Logger LOGGER_JUL = java.util.logging.Logger.getLogger(VersionServlet.class.getName());

    private final String MENIFEST = "/META-INF/MANIFEST.MF";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        LOG4J2.debug("log4j2 debug Called.");
        LOG4J2.info("log4j2 info Called.");
        LOGGER_JUL.finest("JUL finest Called.");
        LOGGER_JUL.finer("JUL finer Called.");
        LOGGER_JUL.fine("JUL fine Called.");
        LOGGER_JUL.info("JUL info Called.");
        LOGGER_JUL.severe("JUL severe Called.");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
        response.setDateHeader("Expires", 0); //prevents caching at the proxy server

        try (PrintWriter out = response.getWriter()) {
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
        }

        //testDatabase();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    /*
    protected void testDatabase() {
    
        log.info("Testing database.");
    
        try (EntityManagerAutoCloseable entityManager = AttServerModule.getInstance(EntityManagerAutoCloseable.class)) {
    
            Query query = entityManager.getEntityManager().createNativeQuery("select current_date");
    
            Date date = (Date) query.getSingleResult();
    
            log.info("Date from database: " + date);
        } catch (Throwable t) {
            log.error("Exception select date from database.", t);
        }
    }
    */
}
