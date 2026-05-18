package com.learn.jsf.filter;


import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;


public final class AccessControllerFilter
   extends FilterBase
{
   protected static final Logger LOG = Logger.getLogger(AccessControllerFilter.class);

   public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException
   {
      LOG.info("called.");

      if (getFilterConfig() == null || request == null)
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

      chain.doFilter(request, response);
   }
}
