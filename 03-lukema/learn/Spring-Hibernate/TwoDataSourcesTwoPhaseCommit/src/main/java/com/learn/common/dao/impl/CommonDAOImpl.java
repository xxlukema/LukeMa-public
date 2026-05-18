package com.learn.common.dao.impl;


import java.util.List;

import org.hibernate.SessionFactory;

import com.learn.bean.BeanBase;
import com.learn.common.dao.CommonDAO;


public abstract class CommonDAOImpl
    implements CommonDAO {

    public abstract SessionFactory getSessionFactory();

    public abstract void setSessionFactory(SessionFactory sessionFactory);

    @SuppressWarnings("unchecked")
    public <T extends BeanBase> List<T> list(Class<T> clazz) {
        return getSessionFactory().getCurrentSession().createCriteria(clazz).list();
    }

    public <T extends BeanBase> T saveOrUpdate(T bean) {
        getSessionFactory().getCurrentSession().saveOrUpdate(bean);

        return bean;
    }

}
