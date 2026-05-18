package com.learn.service;


import java.util.List;

import com.learn.bean.mysql.MySQLObject;
import com.learn.bean.mysql.Widget;
import com.learn.bean.oracle.OracleObject;
import com.learn.bean.oracle.Person;
import com.learn.mysql.dao.WidgetDAO;
import com.learn.oracle.dao.PersonDAO;


public class MyServiceImpl
   implements MyService
{
   private PersonDAO personDAO;

   private WidgetDAO widgetDAO;

   public PersonDAO getPersonDAO()
   {
      return personDAO;
   }

   /**
    * @see MyService#setPersonDAO(PersonDAO)
    */
   public void setPersonDAO(PersonDAO personDAO)
   {
      this.personDAO = personDAO;
   }

   /**
    * @see MyService#setWidgetDAO(WidgetDAO)
    */
   public void setWidgetDAO(WidgetDAO widgetDAO)
   {
      this.widgetDAO = widgetDAO;
   }

   public WidgetDAO getWidgetDAO()
   {
      return widgetDAO;
   }

   private void doUpdate(String newName)
      throws Exception
   {
      // Oracle Person
      PersonDAO personDAO = getPersonDAO();

      List<Person> people = personDAO.list();

      for (Person person : people)
      {
         person.setName(newName);
         personDAO.saveOrUpdate((OracleObject) person);
      }

      // MySQL Widget
      WidgetDAO widgetDAO = getWidgetDAO();

      List<Widget> widgets = widgetDAO.list();

      for (Widget widget : widgets)
      {
         widget.setName(newName);
         widgetDAO.saveOrUpdate((MySQLObject) widget);
      }
   }

   /**
    * @see MyService#updatePersonRollback()
    */
   public void updatePersonRollback()
      throws Exception
   {
      doUpdate("Rollback. Not Show.");

      throw new AppException("This will cause Rollback");
   }

   /**
    * @see MyService#updatePersonCommit()
    */
   public void updatePersonCommit()
      throws Exception
   {
      doUpdate("Commit. Show New Name.");

      throw new Exception("This will not affact transaction.");
   }

   public void saveObjects(OracleObject oracleObject, MySQLObject mySQLObject)
      throws Exception
   {
      // Oracle Person
      getPersonDAO().saveOrUpdate(oracleObject);

      // MySQL Widget
      getWidgetDAO().saveOrUpdate(mySQLObject);
   }

}
