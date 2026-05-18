package com.learn;


import java.util.List;

import org.springframework.beans.factory.*;
import org.springframework.beans.factory.xml.*;
import org.springframework.core.io.*;

import org.apache.log4j.Logger;


public class WidgetDAOTransactionClient  
{
   private static final Logger LOG = Logger.getLogger(WidgetDAOTransactionClient.class);

   private static WidgetDAO WIDGET_DAO = null;

   static
   {
      try
      {
         LOG.info("Creating ClassPathResource...");

         Resource  res = new ClassPathResource("WidgetDAOClient.xml");

         LOG.info("Creating XmlBeanFactory using the above resource...");
         BeanFactory beanFactory  = new XmlBeanFactory(res);

         LOG.info("Calling factory.getBean...");

         WIDGET_DAO = (WidgetDAO) (beanFactory.getBean("widgetDAO"));

         LOG.info("Got the DAO bean.");
      }
      catch (Throwable t)
      {
         LOG.error(t);
      }
   }

   public static void main(String args[])
   {
      //addRecord();

      //addRecordNameIsNull();

      findWidgets();
   }

   ///////////////////////////////////////////////////////
   /// Declarative Transaction management
   ///////////////////////////////////////////////////////
   public static void addRecord()
   {
      Widget widget=new Widget();
      widget.setName("Luke's Widget");
      widget.setSize(10);

      try
      {
         WIDGET_DAO.saveWidget(widget);
      }
      catch (Throwable t)
      {
         LOG.error(t);
      }
   }

   public static void addRecordNameIsNull()
   {
      Widget widget=new Widget();
      widget.setSize(20);

      try
      {
         WIDGET_DAO.saveWidget(widget);
      }
      catch (Throwable t)
      {
         LOG.error(t);
      }
   }

   ///////////////////////////////////////////////////////
   /// Declarative Transaction management
   ///////////////////////////////////////////////////////
   public static void displayWidgets()
   {
      WIDGET_DAO.displayWidgets();
   }

   public static void findWidgets()
   {
      String name = "Luke's Widget";

      LOG.info("Widgets with name = " + name);

      List<Widget> widgets = WIDGET_DAO.findByName(name);

      LOG.debug("Number of records in widget table: " + widgets.size());

      for (Widget w : widgets)
      {
         String s = w.toString();
         LOG.info(s);
      }

      name = null;

      LOG.info("Widgets with name = " + name);

      widgets = WIDGET_DAO.findByName(name);

      LOG.debug("Number of records in widget table: " + widgets.size());

      for (Widget w : widgets)
      {
         String s = w.toString();
         LOG.info(s);
      }
   }
}
