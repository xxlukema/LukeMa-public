package com.learn.session.tx;


import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.learn.util.EjbConstants;
import com.learn.entity.Movie;
import com.learn.session.interceptor.MyBeanInterceptor;


@Stateless
@DeclareRoles( { "Manager", "Employee" })
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors ({MyBeanInterceptor.class})
public class MovieSessionBeanMandatory
   implements MovieSessionBeanMandatoryLocal, MovieSessionBeanMandatoryRemote
{
   private static final long     serialVersionUID = 1L;

   protected static final Logger LOG              = Logger.getLogger(MovieSessionBeanMandatory.class);

   //@PersistenceContext
   @PersistenceContext(unitName = EjbConstants.UnitName)
   EntityManager                 entityManager;

   @Override
   // Default @PermitAll 
   public void addMovie(Movie movie)
      throws Exception
   {
      entityManager.persist(movie);
   }

   @Override
   @RolesAllowed( { "Employee", "Manager" })
   public void updateMovie(Movie movie)
      throws Exception
   {
      entityManager.merge(movie);
   }

   @Override
   @RolesAllowed( { "Manager" })
   public void deleteMovie(Movie movie)
      throws Exception
   {
      movie = entityManager.merge(movie);
      entityManager.remove(movie);
   }

   @SuppressWarnings("unchecked")
   @Override
   @PermitAll
   public List<Movie> getMovies()
      throws Exception
   {
      Query query = entityManager.createQuery("from Movie");
      return query.getResultList();
   }

   @AroundInvoke
   public Object myInterceptor(InvocationContext invocationContext)
      throws Exception
   {
      LOG.info("Before call - Method");
      Object result = invocationContext.proceed();
      LOG.info("After call - Method");
      return result;
   }

}
