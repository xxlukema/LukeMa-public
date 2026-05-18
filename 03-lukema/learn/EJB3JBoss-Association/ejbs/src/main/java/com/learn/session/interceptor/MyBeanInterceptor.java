package com.learn.session.interceptor;


import java.lang.reflect.Method;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;


public class MyBeanInterceptor
{
   protected static final Logger LOG = Logger.getLogger(MyBeanInterceptor.class);

   @Resource
   SessionContext                sessionContext;

   @AroundInvoke
   public Object intercept(InvocationContext invocationContext)
      throws Exception
   {
      LOG.info("Before call - Class");

      if (sessionContext == null)
      {
         LOG.error("SessionContext is null.");
      }
      else
      {
         LOG.info("SessionContext is injected.");

         /* Principal principal = sessionContext.getCallerPrincipal();
          String name = principal.getName();

          LOG.info("Name: " + name);

          if (sessionContext.isCallerInRole("Employee"))
          {
             LOG.info("User is in Employee Role.");
          }
          else
          {
             LOG.info("User is NOT in Employee Role.");
          }*/
      }

      try
      {
         Method method = invocationContext.getMethod();
         LOG.info("Method: " + method.getName());
         Object[] parameters = invocationContext.getParameters();
         for (Object parameter : parameters)
         {
            LOG.info("parameter: " + parameter.getClass().getName());
         }

         Object result = invocationContext.proceed();
         return result;
      }
      finally
      {
         LOG.info("After call - Class");
      }
   }
}
