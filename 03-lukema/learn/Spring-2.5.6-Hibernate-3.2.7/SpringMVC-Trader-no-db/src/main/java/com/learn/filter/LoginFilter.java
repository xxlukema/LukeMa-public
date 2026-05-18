
package com.learn.filter;


import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.log4j.Logger;


public final class LoginFilter
implements Filter
{
   private static final Logger LOG = Logger.getLogger(LoginFilter.class);

   private FilterConfig filterConfig = null;

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

      if (filterConfig == null)
      {
         return;
      }

      if (request instanceof HttpServletRequest)
      {
         HttpServletRequest httpRequest = (HttpServletRequest) request;

         if (httpRequest != null)
         {
            HttpSession session = httpRequest.getSession(false);

            if (session != null)
            {
               String usr = (String) session.getAttribute("usr");

               if (usr != null && usr.equals("guest"))
               {
                  LOG.info("User "+usr+" is logged in.");
               }
               else
               {
                  LOG.info("User NOT logged in.");
                  
                  if (request instanceof HttpServletRequest)
                  {
                     String servletPath = ((HttpServletRequest) request).getServletPath();
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
               }
            }
         }
      }

      chain.doFilter(request, response);
   }
}
