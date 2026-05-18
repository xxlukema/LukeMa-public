package com.learn.dao.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.learn.bean.BeanBase;
import com.learn.dao.CommonDAO;


public class CommonDAOImpl
   extends HibernateDaoSupport
   implements CommonDAO
{
   private static final Logger LOG = Logger.getLogger(CustomerDAOImpl.class);

   @SuppressWarnings("unchecked")
   public <T extends BeanBase> List<T> list(Class<T> clazz)
   {
      LOG.info("Entering function.");

      return getHibernateTemplate().loadAll(clazz);
   }

   public <T extends BeanBase> T saveOrUpdate(T bean)
   {
      LOG.info("Entering function.");

      getHibernateTemplate().saveOrUpdate(bean);

      return bean;
   }

   public <T extends BeanBase> void delete(T bean)
   {
      LOG.info("Entering function.");

      getHibernateTemplate().delete(bean);
   }

}
