package com.learn.dao.impl;


import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.learn.bean.BeanBase;
import com.learn.dao.CommonDAO;


public class CommonDAOImpl
   extends HibernateDaoSupport
   implements CommonDAO
{
   @SuppressWarnings("unchecked")
   public <T extends BeanBase> List<T> list(Class<T> clazz)
   {
      return getHibernateTemplate().loadAll(clazz);
   }

   public <T extends BeanBase> T saveOrUpdate(T bean)
   {
      getHibernateTemplate().saveOrUpdate(bean);

      return bean;
   }

}
