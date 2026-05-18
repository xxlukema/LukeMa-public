package com.learn.dao.impl;


import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.learn.bean.BeanBase;
import com.learn.dao.CommonDAO;


@Repository("commonDAO")
public class CommonDAOImpl
    implements CommonDAO {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public <T extends BeanBase> List<T> list(Class<T> clazz) {
        LOG.info("Entering function.");

        CriteriaBuilder builder = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(clazz);
        criteriaQuery.select(criteriaQuery.from(clazz));

        Query<T> query = sessionFactory.getCurrentSession().createQuery(criteriaQuery);

        return query.list();
    }

    public <T extends BeanBase> T saveOrUpdate(T bean) {
        LOG.info("Entering function.");

        sessionFactory.getCurrentSession().saveOrUpdate(bean);

        return bean;
    }

    public <T extends BeanBase> void delete(T bean) {
        LOG.info("Entering function.");

        sessionFactory.getCurrentSession().delete(bean);
    }

}
