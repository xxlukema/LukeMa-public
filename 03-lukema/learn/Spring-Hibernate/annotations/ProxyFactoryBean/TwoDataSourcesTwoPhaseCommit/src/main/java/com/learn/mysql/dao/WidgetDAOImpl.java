package com.learn.mysql.dao;


import java.util.List;

import com.learn.bean.mysql.MySQLObject;
import com.learn.bean.mysql.Widget;
import com.learn.common.dao.CommonDAOImpl;


public class WidgetDAOImpl
   extends CommonDAOImpl
   implements WidgetDAO
{
   public List<Widget> list()
      throws Exception
   {
      return super.list(Widget.class);
   }

   public <T extends MySQLObject> T saveOrUpdate(T bean)
      throws Exception
   {
      getHibernateTemplate().saveOrUpdate(bean);

      return bean;
   }
}
