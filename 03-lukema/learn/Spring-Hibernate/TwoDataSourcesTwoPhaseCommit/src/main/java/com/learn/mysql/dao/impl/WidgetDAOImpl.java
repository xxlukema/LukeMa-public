package com.learn.mysql.dao.impl;


import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.learn.common.dao.impl.CommonDAOImpl;


@Repository("widgetDAO")
public class WidgetDAOImpl
    extends CommonDAOImpl {

    @Autowired
    @Qualifier("mysqlSessionFactory")
    private SessionFactory sessionFactory;

    @Override
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
