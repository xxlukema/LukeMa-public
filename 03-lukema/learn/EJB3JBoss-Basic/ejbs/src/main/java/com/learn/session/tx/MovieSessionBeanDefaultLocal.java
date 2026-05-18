package com.learn.session.tx;


import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import com.learn.entity.Movie;


@Local
public interface MovieSessionBeanDefaultLocal
   extends Serializable
{
   public void addMovie(Movie movie)
      throws Exception;

   public void updateMovie(Movie movie)
      throws Exception;

   public void deleteMovie(Movie movie)
      throws Exception;

   public List<Movie> getMovies()
      throws Exception;
}
