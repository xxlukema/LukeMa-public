package com.learn.test.dao;


import java.util.Date;
import java.util.List;

import javax.naming.NamingException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.learn.dao.impl.MovieDao;
import com.learn.entity.Movie;
import com.learn.util.JulConfigReader;


/**
 * 
 * https://netbeans.org/kb/docs/javaee/javaee-entapp-junit.html
 * 
 * @author lma
 *
 */

public class MovieDaoTest {

    private static final Logger LOG = LogManager.getLogger(MovieDaoTest.class);

    @BeforeClass
    public static void beforeClass() {
        JulConfigReader.readConfig();
    }

    @BeforeTest
    public void setup() {

    }

    @AfterTest
    public void tearDown()
        throws NamingException {

    }

    @Test(enabled = false)
    public void testAdd()
        throws Exception {
        LOG.info("Begin Test");

        MovieDao movieDao = new MovieDao();

        Movie movie = new Movie();
        movie.setDirector("Luke Ma");
        movie.setTitle("Happy Day");
        Date date = new Date();
        movie.setCreateDate(date);
        movie.setUpdateDate(date);
        movieDao.addMovie(movie);

        movieDao.getEntityManager().close();

        LOG.info("End Test");
    }

    @Test(invocationCount = 1000, threadPoolSize = 10)
    public void testGet()
        throws Exception {
        //LOG.info("Begin Test");

        MovieDao movieDao = new MovieDao();

        List<Movie> movies = movieDao.getMovies();

        //Assert.assertTrue("movies.size()", (movies.size() == 1));
        Assert.assertTrue((movies.size() == 1));

        Movie movie = movies.get(0);

        //LOG.info(movie.getTitle());
        Assert.assertTrue("Happy Day".equals(movie.getTitle()));

        //movieDao.getEntityManager().close();

        //LOG.info("End Test");
    }
}
