package com.learn.persistence.dao.impl;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.learn.persistence.bean.BeanBase;
import com.learn.persistence.dao.CommonDAO;


public class CommonDAOImpl
   extends HibernateDaoSupport
   implements CommonDAO
{
   protected static final Logger LOG = Logger.getLogger(UserDAOImpl.class);

   public <T extends BeanBase> List<T> list(Class<T> clazz)
   {
      return getHibernateTemplate().loadAll(clazz);
   }

   public <T extends BeanBase> T saveOrUpdate(T bean)
   {
      Date date = new Date();
      if (bean.getId() == null)
      {
         bean.setDateCreated(date);
      }
      bean.setDateUpdated(date);
      getHibernateTemplate().saveOrUpdate(bean);

      return bean;
   }

   public <T extends BeanBase> void delete(T bean)
   {
      getHibernateTemplate().delete(bean);
   }

   public <T extends BeanBase> T save(T bean)
   {
      Date date = new Date();
      bean.setDateCreated(date);
      bean.setDateUpdated(date);

      getHibernateTemplate().save(bean);
      return bean;
   }

   public <T extends BeanBase> T update(T bean)
   {
      Date date = new Date();
      bean.setDateUpdated(date);
      getHibernateTemplate().update(bean);
      return bean;
   }

}
