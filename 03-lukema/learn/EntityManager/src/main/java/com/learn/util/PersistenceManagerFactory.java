package com.learn.util;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PersistenceManagerFactory {

    private static final Logger LOG = LogManager.getLogger(PersistenceManagerFactory.class);

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(EjbConstants.PERSISTENCE_UNIT_NAME);

    public static final EntityManager getEntityManager() {
        return EntityManagerCreator.get();
    }

    protected static ThreadLocal<EntityManager> EntityManagerCreator = new ThreadLocal<EntityManager>() {
        @Override
        protected EntityManager initialValue() {
            LOG.debug("PersistenceManagerFactory.EntityManagerCreator.initialValue()");
            return entityManagerFactory.createEntityManager();
        }
    };
}
