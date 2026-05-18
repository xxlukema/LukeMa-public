package com.learn.common.dao;


import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.learn.bean.BeanBase;


public class CommonDAOImpl
   extends HibernateDaoSupport
   implements CommonDAO
{
   @SuppressWarnings("unchecked")
   public <T extends BeanBase> List<T> list(Class<T> clazz)
      throws Exception
   {
      return getHibernateTemplate().loadAll(clazz);
   }

   public <T extends BeanBase> T saveOrUpdate(T bean)
      throws Exception
   {
      getHibernateTemplate().saveOrUpdate(bean);

      return bean;
   }

}
