package com.learn.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import com.learn.session.User;


public final class LoginFilter
    implements Filter {
    private static final Logger LOG = LogManager.getLogger();

    private FilterConfig filterConfig = null;

    public void init(FilterConfig filterConfig)
        throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        LOG.info("called.");

        if (filterConfig == null || request == null) {
            return;
        }

        if (!(request instanceof HttpServletRequest)) {
            return;
        }

        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpSession session = httpRequest.getSession(false);

        if (session == null) {
            return;
        }

        /*@SuppressWarnings("unchecked")
        Enumeration<String> enumeration = session.getAttributeNames();

        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            LOG.info("##### ============== name = " + name);
        }*/

        User user = (User) session.getAttribute("scopedTarget.user");

        if (user == null) {
            LOG.info("###################### Session attribute not found: User.");
        } else {

            String username = user.getUsername();

            if (username != null && username.equals("guest")) {
                LOG.info("User " + username + " is logged in.");
            } else {
                LOG.info("User NOT logged in.");

                String servletPath = httpRequest.getServletPath();
                LOG.info("Servlet Path: " + servletPath);

                if (filterConfig != null) {

                    if (response instanceof HttpServletResponse) {
                        LOG.info("Is HttpServletResponse.");

                        String redirect = filterConfig.getInitParameter("redirect");
                        LOG.info("Redirect: " + redirect);

                        if (!servletPath.endsWith(redirect)) {
                            LOG.info("Not logon page. Redirect to Logon.go page.");

                            ((HttpServletResponse) response).sendRedirect(redirect);
                        }
                    }
                }
            }
        }

        chain.doFilter(request, response);
    }
}
