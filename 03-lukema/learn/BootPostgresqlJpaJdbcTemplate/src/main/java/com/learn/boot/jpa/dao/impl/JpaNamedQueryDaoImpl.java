package com.learn.boot.jpa.dao.impl;


import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.learn.boot.jpa.dao.NamedQueryJpaDao;
import com.learn.boot.jpa.pojo.SysdatePojo;
import com.learn.util.EjbConstants;


/**
 * SQL and Parameter log levels:
 * 
 * DEBUG org.hibernate
 * TRACE org.hibernate.type
 * 
 * 2018-03-12 16:27:26 DEBUG org.hibernate.SQL(92) logStatement() 
 *    -- selectCurrentDate -- SELECT
 *        current_date as date,
 *        'desc' as note
 *    where
 *        100 != ?
 *        and current_date != ?
 * 2018-03-12 16:27:26 TRACE org.hibernate.type.descriptor.sql.BasicBinder(65) bind()
 * binding parameter [2] as [TIMESTAMP] - [Fri Jan 12 16:27:26 EST 2018]
 * 2018-03-12 16:27:26 TRACE org.hibernate.type.descriptor.sql.BasicBinder(65) bind()
 * binding parameter [1] as [INTEGER] - [1]
 * 
 */
@Repository("namedQueryJpaDao")
public class JpaNamedQueryDaoImpl
    implements NamedQueryJpaDao {

    private static final Logger LOG = LogManager.getLogger();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public SysdatePojo selectCurrentDateJpa(Map<String, Object> namedParameters) {
        LOG.info("Entering function.");

        TypedQuery<SysdatePojo> query = entityManager.createNamedQuery(EjbConstants.Select_Current_Date, SysdatePojo.class);

        // @formatter:off
        query.setParameter("dat", namedParameters.get("dat"))
             .setParameter("num", namedParameters.get("num"));
        // @formatter:on

        return query.getSingleResult();
    }

}
