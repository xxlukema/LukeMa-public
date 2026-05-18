package com.learn.service.impl;


import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;

import com.learn.bean.mysql.MySQLObject;
import com.learn.bean.mysql.Widget;
import com.learn.bean.oracle.OracleObject;
import com.learn.bean.oracle.Person;
import com.learn.mysql.dao.WidgetDAO;
import com.learn.oracle.dao.PersonDAO;
import com.learn.service.AppException;
import com.learn.service.MyService;


public class MyServiceImpl
   implements MyService
{
   private static final Logger LOG = Logger.getLogger(MyServiceImpl.class);

   private PersonDAO           personDAO;

   private WidgetDAO           widgetDAO;

   public PersonDAO getPersonDAO()
   {
      return personDAO;
   }

   public void setPersonDAO(PersonDAO personDAO)
   {
      this.personDAO = personDAO;
   }

   public void setWidgetDAO(WidgetDAO widgetDAO)
   {
      this.widgetDAO = widgetDAO;
   }

   public WidgetDAO getWidgetDAO()
   {
      return widgetDAO;
   }

   public void list()
      throws Exception
   {
      List<Person> people = getPersonDAO().list();
      Assert.assertTrue(people.size() > 0);
      LOG.info("people.size() = " + people.size());

      for (Person person : people)
      {
         LOG.info("Person name = " + person.getName());
      }

      List<Widget> widgets = getWidgetDAO().list();
      Assert.assertTrue(widgets.size() > 0);
      LOG.info("widgets.size() = " + widgets.size());

      for (Widget widget : widgets)
      {
         LOG.info("Widget name = " + widget.getName());
      }
   }

   public void saveObjects(OracleObject oracleObject, MySQLObject mySQLObject)
      throws Exception
   {
      // Oracle Person
      getPersonDAO().saveOrUpdate(oracleObject);

      // MySQL Widget
      getWidgetDAO().saveOrUpdate(mySQLObject);

      throw new Exception("This will not affact transaction.");
   }

   public void saveObjectsRollback(OracleObject oracleObject, MySQLObject mySQLObject)
      throws Exception
   {
      // Oracle Person
      getPersonDAO().saveOrUpdate(oracleObject);

      // MySQL Widget
      getWidgetDAO().saveOrUpdate(mySQLObject);

      throw new AppException("This will cause Rollback");
   }
}
