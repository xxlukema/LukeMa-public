package com.learn;


import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import org.apache.log4j.Logger;


public class WidgetDAOHibernateImpl 
extends HibernateDaoSupport 
implements WidgetDAO
{
   private static final Logger LOG = Logger.getLogger(WidgetDAOHibernateImpl.class);

   public void displayWidgets()
   {
      List<Widget> c = getWidgets();

      LOG.debug("Number of records in widget table: " + c.size());

      for (Widget w : c)
      {
         String s = w.toString();
         LOG.info(s);
      }
   }

   public List<Widget> getWidgets()
   {
      return getHibernateTemplate().loadAll(Widget.class);
   }

   public Widget getWidgetById(Long id)
   {
      return(Widget) getHibernateTemplate().get(Widget.class, id); 
   }

   public Widget saveWidget(Widget widget)
   {
      getHibernateTemplate().save(widget);
      return widget;
   }

   public Widget saveOrUpdateWidget(Widget widget)
   {
      getHibernateTemplate().saveOrUpdate(widget);
      return widget;
   }

   public Widget updateWidget(Widget widget)
   {
      getHibernateTemplate().update(widget);
      return widget;
   }

   public void deleteWidget(Long id)
   {
      Widget widget = getWidgetById(id);
      deleteWidget(widget);
   }

   public void deleteWidget(Widget widget)
   {
      getHibernateTemplate().delete(widget);
   }

   public void testHQL()
   {
   }
}



