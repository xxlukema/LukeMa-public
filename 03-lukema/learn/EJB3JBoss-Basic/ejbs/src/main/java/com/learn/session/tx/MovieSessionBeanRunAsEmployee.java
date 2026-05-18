package com.learn.session.tx;


import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

import org.apache.log4j.Logger;

import com.learn.entity.Movie;


@Stateless
@DeclareRoles( { "Employee", "Manager" })
@RunAs("Employee")
public class MovieSessionBeanRunAsEmployee
   implements MovieSessionBeanRunAsEmployeeLocal, MovieSessionBeanRunAsEmployeeRemote
{
   private static final long            serialVersionUID = 1L;

   protected static final Logger        LOG              = Logger.getLogger(MovieSessionBeanRunAsEmployee.class);

   @Resource
   SessionContext                       sessionContext;

   @EJB
   private MovieSessionBeanDefaultLocal movieSessionBeanDefaultLocal;

   @Override
   public void addMovie(Movie movie)
      throws Exception
   {
      movieSessionBeanDefaultLocal.addMovie(movie);
   }

   @Override
   public void updateMovie(Movie movie)
      throws Exception
   {
      movieSessionBeanDefaultLocal.updateMovie(movie);
   }

   @Override
   public void deleteMovie(Movie movie)
      throws Exception
   {
      movieSessionBeanDefaultLocal.deleteMovie(movie);
   }

   @Override
   public List<Movie> getMovies()
      throws Exception
   {
      return movieSessionBeanDefaultLocal.getMovies();
   }

   @AroundInvoke
   public Object myInterceptor(InvocationContext invocationContext)
      throws Exception
   {
      LOG.info("Before call - Method");
      try
      {
         Method method = invocationContext.getMethod();
         LOG.info("Method: " + method.getName());

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

         Object result = invocationContext.proceed();
         return result;
      }
      finally
      {
         LOG.info("After call - Method");
      }
   }
}
