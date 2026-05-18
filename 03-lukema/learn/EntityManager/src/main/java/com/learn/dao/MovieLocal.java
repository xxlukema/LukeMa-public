package com.learn.dao;


import java.util.List;

import javax.ejb.Local;

import com.learn.entity.Movie;


@Local
public interface MovieLocal {

    // Default @PermitAll 
    public abstract void addMovie(Movie movie)
        throws Exception;

    public abstract void updateMovie(Movie movie)
        throws Exception;

    public abstract void deleteMovie(Movie movie)
        throws Exception;

    public abstract List<Movie> getMovies()
        throws Exception;

}
