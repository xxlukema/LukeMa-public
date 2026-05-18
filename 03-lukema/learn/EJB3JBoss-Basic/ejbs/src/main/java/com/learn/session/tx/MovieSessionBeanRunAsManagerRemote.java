package com.learn.session.tx;


import java.io.Serializable;
import java.util.List;

import javax.ejb.Remote;

import com.learn.entity.Movie;


@Remote
public interface MovieSessionBeanRunAsManagerRemote
   extends Serializable
{
   public void addMovie(Movie movie)
      throws Exception;

   public void deleteMovie(Movie movie)
      throws Exception;

   public void updateMovie(Movie movie)
      throws Exception;

   public List<Movie> getMovies()
      throws Exception;
}
