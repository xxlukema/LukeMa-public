package com.learn.oracle.dao;


import java.util.List;

import com.learn.bean.oracle.OracleObject;
import com.learn.bean.oracle.Person;
import com.learn.common.dao.CommonDAOImpl;


public class PersonDAOImpl
   extends CommonDAOImpl
   implements PersonDAO
{
   public List<Person> list()
      throws Exception
   {
      return super.list(Person.class);
   }

   public <T extends OracleObject> T saveOrUpdate(T bean)
      throws Exception
   {
      getHibernateTemplate().saveOrUpdate(bean);

      return bean;
   }
}
