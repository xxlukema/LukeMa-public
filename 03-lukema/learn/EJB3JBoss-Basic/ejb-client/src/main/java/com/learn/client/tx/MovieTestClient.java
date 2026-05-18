package com.learn.client.tx;


import java.util.Date;
import java.util.List;

import javax.ejb.EJBAccessException;
import javax.ejb.EJBTransactionRequiredException;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.learn.entity.Movie;
import com.learn.session.tx.MovieSessionBeanMandatoryRemote;
import com.learn.session.tx.MovieSessionBeanRunAsEmployeeRemote;
import com.learn.session.tx.MovieSessionBeanRunAsManagerRemote;
import com.learn.util.EjbLookupUtils;
import com.learn.util.LookupConstants;
import com.learn.util.LookupUtils;


public class MovieTestClient {
    protected static final Logger LOG = Logger.getLogger(MovieTestClient.class);

    @Test
    public void testDefault()
        throws Exception {

        MovieSessionBeanMandatoryRemote beanRemote = EjbLookupUtils
                .lookup(LookupConstants.JNDI_Movie_Default);

        // Add Movie
        Movie movie = new Movie();
        movie.setTitle("Ju Dou");
        movie.setDirector("Zhang Yi Mu");
        movie.setCreateDate(new Date());
        movie.setUpdateDate(new Date());

        beanRemote.addMovie(movie);

        List<Movie> movies = beanRemote.getMovies();
        LOG.info("movies.size() = " + movies.size());
    }

    public void testRunAsManager()
        throws Exception {
        Context context = LookupUtils.getInitialContext();

        Object object = context.lookup(LookupConstants.JNDI_Movie_Manager);

        MovieSessionBeanRunAsManagerRemote beanRemote = (MovieSessionBeanRunAsManagerRemote) PortableRemoteObject
                .narrow(object, MovieSessionBeanRunAsManagerRemote.class);

        // Add Movie
        Movie movie = new Movie();
        movie.setTitle("Ju Dou");
        movie.setDirector("Zhang Yi Mu");
        movie.setCreateDate(new Date());
        movie.setUpdateDate(new Date());

        beanRemote.addMovie(movie);

        // Update Movie
        List<Movie> movies = beanRemote.getMovies();
        LOG.info("movies.size() = " + movies.size());

        for (Movie mv : movies) {
            if (mv.getTitle().equals("Ju Dou")) {
                mv.setDirector("Zhang Yi-Mu");
                beanRemote.updateMovie(mv);
            }
        }

        // Delete Movie
        for (Movie mv : movies) {
            if (mv.getTitle().equals("Ju Dou")) {
                beanRemote.deleteMovie(mv);
            }
        }

        movies = beanRemote.getMovies();
        LOG.info("movies.size() = " + movies.size());
        for (Movie mv : movies) {
            LOG.info(mv);
        }
    }

    public void testRunAsEmployee()
        throws Exception {
        Context context = LookupUtils.getInitialContext();

        Object object = context.lookup(LookupConstants.JNDI_Movie_Employee);

        MovieSessionBeanRunAsEmployeeRemote beanRemote = (MovieSessionBeanRunAsEmployeeRemote) PortableRemoteObject
                .narrow(object, MovieSessionBeanRunAsEmployeeRemote.class);

        // Add Movie
        Movie movie = new Movie();
        movie.setTitle("Fei Cheng Wu Rao");
        movie.setDirector("Feng Xiao Gang");
        movie.setCreateDate(new Date());
        movie.setUpdateDate(new Date());

        beanRemote.addMovie(movie);

        // Update Movie
        List<Movie> movies = beanRemote.getMovies();
        LOG.info("movies.size() = " + movies.size());

        for (Movie mv : movies) {
            if (mv.getTitle().equals("Fei Cheng Wu Rao")) {
                mv.setDirector("Feng Xiao-Gang");
                beanRemote.updateMovie(mv);
            }
        }

        // Delete Movie
        for (Movie mv : movies) {
            if (mv.getTitle().equals("Fei Cheng Wu Rao")) {
                try {
                    beanRemote.deleteMovie(mv);
                    TestCase.fail("Delete is not allowed for employee.");
                }
                catch (EJBAccessException e) {
                    LOG.info("PASS!", e);
                }
            }
        }

        movies = beanRemote.getMovies();
        for (Movie mv : movies) {
            LOG.info(mv);
        }
    }

    public void testMandatoryWithoutSession()
        throws Exception {

        MovieSessionBeanMandatoryRemote beanRemote = EjbLookupUtils
                .lookup(LookupConstants.JNDI_Movie_Mandatory);

        try {
            List<Movie> movies = beanRemote.getMovies();
            LOG.info("movies.size() = " + movies.size());
            TestCase.fail("EJBTransactionRequiredException should be thrown.");
        }
        catch (EJBTransactionRequiredException e) {
            LOG.info("Caught exception that should be thrown: ", e);
        }
    }

}
