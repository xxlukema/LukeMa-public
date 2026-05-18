package com.learn.dao.impl;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.dao.MovieLocal;
import com.learn.entity.Movie;
import com.learn.interceptor.MyBeanInterceptor;
import com.learn.util.EjbConstants;
import com.learn.util.PersistenceManagerFactory;


@Stateless
@EJB(beanName = "MovieDao", name = "MovieSessionBean", beanInterface = MovieLocal.class)
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors({ MyBeanInterceptor.class })
public class MovieDao
    implements Serializable, MovieLocal {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger(MovieDao.class);

    //@PersistenceContext
    @PersistenceContext(unitName = EjbConstants.PERSISTENCE_UNIT_NAME)
    private final EntityManager entityManager = PersistenceManagerFactory.getEntityManager();

    @PostConstruct
    public void postConstruct() {
        LOG.info("MovieDao.postConstruct()");
    }

    @PreDestroy
    public void preDestroy() {
        LOG.info("MovieDao.preDestroy()");
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    // Default @PermitAll 
    /* (non-Javadoc)
     * @see com.learn.dao.impl.MovieLocal#addMovie(com.learn.entity.Movie)
     */
    @Override
    public void addMovie(Movie movie)
        throws Exception {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(movie);
            entityTransaction.commit();
        } catch (Exception e) {
            LOG.error("Unable to persist.", e);
            entityTransaction.rollback();
        }
    }

    /* (non-Javadoc)
     * @see com.learn.dao.impl.MovieLocal#updateMovie(com.learn.entity.Movie)
     */
    @Override
    public void updateMovie(Movie movie)
        throws Exception {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityManager.merge(movie);
            entityTransaction.commit();
        } catch (Exception e) {
            LOG.error("Unable to merge.", e);
            entityTransaction.rollback();
        }
    }

    /* (non-Javadoc)
     * @see com.learn.dao.impl.MovieLocal#deleteMovie(com.learn.entity.Movie)
     */
    @Override
    public void deleteMovie(Movie movie)
        throws Exception {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            movie = entityManager.merge(movie);
            entityManager.remove(movie);
            entityTransaction.commit();
        } catch (Exception e) {
            LOG.error("Unable to save.", e);
            entityTransaction.rollback();
        }
    }

    /* (non-Javadoc)
     * @see com.learn.dao.impl.MovieLocal#getMovies()
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<Movie> getMovies()
        throws Exception {
        Query query = entityManager.createQuery("from Movie");
        return query.getResultList();
    }

    @AroundInvoke
    public Object myInterceptor(InvocationContext invocationContext)
        throws Exception {
        LOG.info("Before call - Method");
        Object result = invocationContext.proceed();
        LOG.info("After call - Method");
        return result;
    }

}
