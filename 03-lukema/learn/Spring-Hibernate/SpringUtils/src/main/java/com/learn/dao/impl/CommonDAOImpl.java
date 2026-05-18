package com.learn.dao.impl;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.learn.bean.BeanBase;
import com.learn.dao.CommonDAO;


@Repository("commonDAO")
public class CommonDAOImpl
    implements CommonDAO {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @SuppressWarnings("unchecked")
    public <T extends BeanBase> List<T> list(Class<T> clazz) {
        Session session = sessionFactory.getCurrentSession();
        List<T> list = session.createCriteria(clazz).list();

        return list;
    }

    public <T extends BeanBase> T saveOrUpdate(T bean) {
        Session session = sessionFactory.getCurrentSession();
        session.saveOrUpdate(bean);

        return bean;
    }

}
