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

import org.apache.log4j.Logger;

import com.learn.util.StringConstants;


public final class LoginFilter
   implements Filter
{
   private static final Logger LOG          = Logger.getLogger(LoginFilter.class);

   private FilterConfig        filterConfig = null;

   public void init(FilterConfig filterConfig)
      throws ServletException
   {
      this.filterConfig = filterConfig;
   }

   public void destroy()
   {
      this.filterConfig = null;
   }

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException
   {
      LOG.info("called.");

      if (filterConfig == null || request == null)
      {
         return;
      }

      if (!(request instanceof HttpServletRequest))
      {
         return;
      }

      HttpServletRequest httpRequest = (HttpServletRequest) request;

      HttpSession session = httpRequest.getSession(false);

      if (session == null)
      {
         return;
      }

      String username = (String) session.getAttribute(StringConstants.SESSION_ATTRIBUTE_USERNAME);

      if (username != null && username.equals("guest"))
      {
         LOG.info("User " + username + " is logged in.");
      }
      else
      {
         LOG.info("User NOT logged in.");

         String servletPath = httpRequest.getServletPath();
         LOG.info("Servlet Path: " + servletPath);

         if (filterConfig != null)
         {
            String redirect = filterConfig.getInitParameter("redirect");

            LOG.info("Redirect: " + redirect);

            if (response instanceof HttpServletResponse)
            {
               LOG.info("Is HttpServletResponse.");

               if (!servletPath.endsWith(redirect))
               {
                  LOG.info("Not logon page. Redirect to logon.go page.");

                  ((HttpServletResponse) response).sendRedirect(redirect);
               }
            }
         }
      }

      chain.doFilter(request, response);
   }
}
