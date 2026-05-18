package com.learn.jsf.filter;


import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


abstract public class FilterBase
   implements Filter
{
   private FilterConfig filterConfig = null;

   public void init(FilterConfig filterConfig)
      throws ServletException
   {
      this.setFilterConfig(filterConfig);
   }

   public void destroy()
   {
      this.setFilterConfig(null);
   }

   abstract public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException;

   public void setFilterConfig(FilterConfig filterConfig)
   {
      this.filterConfig = filterConfig;
   }

   public FilterConfig getFilterConfig()
   {
      return filterConfig;
   }

}
