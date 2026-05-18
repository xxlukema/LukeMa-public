package com.learn.session.tx;


import java.util.List;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RunAs;
import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.apache.log4j.Logger;

import com.learn.entity.Movie;


@Stateless
@DeclareRoles( { "Employee", "Manager" })
@RunAs("Manager")
public class MovieSessionBeanRunAsManager
   implements MovieSessionBeanRunAsManagerLocal, MovieSessionBeanRunAsManagerRemote
{
   private static final long       serialVersionUID = 1L;

   protected static final Logger   LOG              = Logger.getLogger(MovieSessionBeanRunAsManager.class);

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

}
