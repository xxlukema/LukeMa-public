package com.learn;


import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;

import org.apache.log4j.Logger;


/**
 * Priorities: 
 * The annotation closest to the method has the highest priority.
 * Class level annotation has less priority than the method level annotaion.
 * Descriptives in the advisor has the least priority.
 *
 * Defaults:
 * The default propagartion is PROPAGATION_REQUIRED.
 *
 */
// @Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
@Transactional(readOnly = false)
public class WidgetDAOHibernateImpl 
extends HibernateDaoSupport 
implements WidgetDAO
{
   private static final Logger LOG = Logger.getLogger(WidgetDAOHibernateImpl.class);

   @Transactional(readOnly = true)
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

   // The annotation closest to the method has the highest priority.
   // @Transactional(readOnly = false, propagation = Propagation.NOT_SUPPORTED)
   @Transactional(readOnly = false, rollbackFor = MySQLException.class)
   public Widget saveWidget(Widget widget)
   throws Exception
   {
      getHibernateTemplate().save(widget);

      // Throw RuntimeException will cause rollback as defined in advisor.
      // throw new RuntimeException("Test Rollback.");

      // Throw MySQLException will cause rollback as defined in method annotation.
      // throw new MySQLException("Test Rollback.");

      // Throw Exception will NOT cause rollback as defined in advisor.
      // throw new Exception("Test Rollback.");

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

   public List<Widget> findByName(String name)
   {
      String queryName = "widget.findByName";

      String [] parmNames = {"name"};

      Object [] parmValues = {name};

      return getHibernateTemplate().findByNamedQueryAndNamedParam(queryName, parmNames, parmValues);
   }

   public void testHQL()
   {
   }
}



