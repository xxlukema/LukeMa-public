package com.learn.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@WebServlet(name = "MemoryServlet", urlPatterns = { "/mem" })
public class MemoryServlet
    extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = LogManager.getLogger();

    private static final int MemoryInByte = 1024 * 1024;

    private static final String NumberFormat = "#,###,###";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        log.trace("Called.");

        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Cache-Control", "no-cache"); //HTTP 1.1
        response.setHeader("Pragma", "no-cache"); //HTTP 1.0
        response.setDateHeader("Expires", 0); //prevents caching at the proxy server

        try (PrintWriter out = response.getWriter()) {

            Runtime runtime = Runtime.getRuntime();

            long total = runtime.totalMemory();
            long free = runtime.freeMemory();
            long used = total - free;
            long max = runtime.maxMemory();

            String strTotal = toMagBytes(total);
            String strMax = toMagBytes(max);
            String strFree = toMagBytes(free);
            String strUsed = toMagBytes(used);

            out.println("Total: " + strTotal + " MB<br>");
            out.println("Free: " + strFree + " MB<br>");
            out.println("Used: " + strUsed + " MB<br>");
            out.println("Max: " + strMax + " MB<br>");

            log.info("Total: " + strTotal + " MB\n" + "Free: " + strFree + " MB\n" + "Used: " + strUsed + " MB\n" + "Max: " + strMax + " MB");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        processRequest(request, response);
    }

    private String toMagBytes(double bytes) {
        DecimalFormat decimalFormat = new DecimalFormat(NumberFormat);
        String str = decimalFormat.format(bytes / MemoryInByte);
        return str;
    }
}
