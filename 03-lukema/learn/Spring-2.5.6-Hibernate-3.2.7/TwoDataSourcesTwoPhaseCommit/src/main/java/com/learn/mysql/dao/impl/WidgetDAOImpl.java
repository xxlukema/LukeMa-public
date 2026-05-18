package com.learn.mysql.dao.impl;


import java.util.List;

import com.learn.bean.mysql.MySQLObject;
import com.learn.bean.mysql.Widget;
import com.learn.common.dao.impl.CommonDAOImpl;
import com.learn.mysql.dao.WidgetDAO;


public class WidgetDAOImpl
   extends CommonDAOImpl
   implements WidgetDAO
{
   public List<Widget> list()
   {
      return super.list(Widget.class);
   }

   public <T extends MySQLObject> T saveOrUpdate(T bean)
   {
      getHibernateTemplate().saveOrUpdate(bean);

      return bean;
   }
}
