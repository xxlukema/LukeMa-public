package com.learn.persistence.dao.impl;


import java.io.Serializable;
import java.util.Date;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.learn.persistence.bean.BeanBase;
import com.learn.persistence.dao.CommonDAO;


@Repository("commonDAO")
public class CommonDAOImpl
    implements Serializable, CommonDAO {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    /*
     * @SuppressWarnings("unchecked")
    public <T extends BeanBase> List<T> list(Class<T> clazz) {
        return sessionFactory.getCurrentSession().createQuery("from XX").list();
    }*/

    public <T extends BeanBase> T saveOrUpdate(T bean) {
        Date date = new Date();
        if (bean.getId() == null) {
            bean.setCreateDate(date);
        }
        bean.setUpdateDate(date);

        sessionFactory.getCurrentSession().saveOrUpdate(bean);

        return bean;
    }

    public <T extends BeanBase> void delete(T bean) {
        sessionFactory.getCurrentSession().delete(bean);
    }

    public <T extends BeanBase> T save(T bean) {
        Date date = new Date();
        bean.setCreateDate(date);
        bean.setUpdateDate(date);

        sessionFactory.getCurrentSession().save(bean);

        return bean;
    }

    public <T extends BeanBase> T update(T bean) {
        Date date = new Date();
        bean.setUpdateDate(date);

        sessionFactory.getCurrentSession().update(bean);

        return bean;
    }

}
