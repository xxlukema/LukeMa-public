package com.learn;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.*;
import org.springframework.beans.factory.xml.*;
import org.springframework.core.io.*;
import org.springframework.orm.hibernate3.*;
import org.springframework.transaction.*;
import org.springframework.transaction.support.*;

import org.apache.log4j.Logger;


public class WidgetDAOTransactionClient  
{
   private static final Logger LOG = Logger.getLogger(WidgetDAOTransactionClient.class);

   public static void main(String args[])
   {
      try
      {
         LOG.info("Creating ClassPathResource...");

         Resource  res = new ClassPathResource("WidgetDAOClient.xml");

         LOG.info("Creating XmlBeanFactory using the above resource...");
         BeanFactory  factory = new XmlBeanFactory(res);

         LOG.info("Calling factory.getBean...");
         final WidgetDAO widgetDAO = (WidgetDAO) (factory.getBean("widgetDAO"));
         LOG.info("Got the DAO bean.");

         // addRecordNoSessionManagement() will not insert because there is no transaction management.
         // addRecordNoSessionManagement(widgetDAO);

         ///////////////////////////////////////////////////////////////////////
         // Use TransactionTemplate/TransactionCallback to manage transaction.
         ///////////////////////////////////////////////////////////////////////
         testCallbackTransaction(factory, widgetDAO);

         // testDisplay(widgetDAO);
      }
      catch (Exception e1)
      {
         LOG.error(e1);
      }
   }

   ///////////////////////////////////////////////////////////////////////
   // Use TransactionTemplate/TransactionCallback to manage transaction.
   ///////////////////////////////////////////////////////////////////////
   public static void testCallbackTransaction(BeanFactory factory, final WidgetDAO widgetDAO)
   throws Exception
   {
      TransactionTemplate transactionTemplate = (TransactionTemplate) (factory.getBean("transactionTemplate"));

      transactionTemplate.execute(
                                 new TransactionCallback()
                                 {
                                    public Object doInTransaction(TransactionStatus ts)
                                    {
                                       try
                                       {
                                          LOG.info("Adding Tian Tian...");

                                          Widget widget = new Widget();
                                          widget.setName("Tian Tian");
                                          widget.setSize(1);
                                          widgetDAO.saveWidget(widget);

                                          displayWidget(widgetDAO);

                                          widget = widgetDAO.getWidgetById(1L);

                                          if (widget != null)
                                          {
                                             LOG.info("Rename Tian Tian to Huan Huan...");

                                             widget.setName("Huan Huan");
                                             widget.setSize(1);

                                             widgetDAO.saveOrUpdateWidget(widget);

                                             long id = widget.getId();

                                             LOG.info("id: "+id);
                                          }

                                          LOG.info("Adding Luke Ma...");
                                          widget = new Widget();
                                          widget.setName("Luke Ma");
                                          widget.setSize(1);
                                          widgetDAO.saveWidget(widget);

                                          displayWidget(widgetDAO);

                                          LOG.info("Deleting Luke Ma...");

                                          widgetDAO.deleteWidget(widget);

                                          displayWidget(widgetDAO);

                                          // widgetDAO.deleteWidget(id);

                                          // Throwing exception will cause automatic rollback:
                                          // throw new Exception();
                                       }
                                       catch (Exception e)
                                       {
                                          ts.setRollbackOnly();

                                          LOG.error("Exception with Callbak. Rolling back...", e);
                                       }

                                       return null;
                                    }
                                 } );
   }

   ///////////////////////////////////////////////////////////////////////
   // addRecordNoSessionManagement() will not insert because there is no transaction management.
   ///////////////////////////////////////////////////////////////////////
   public static void addRecordNoSessionManagement(WidgetDAO widgetDAO)
   throws Exception
   {
      Widget widget=new Widget();
      widget.setName("Luke's Widget");
      widget.setSize(10);

      widgetDAO.saveWidget(widget);
   }

   ///////////////////////////////////////////////////////////////////////
   // Displays table contents
   ///////////////////////////////////////////////////////////////////////
   public static void displayWidget(WidgetDAO widgetDAO)
   throws Exception
   {
      Long id = new Long(1L);

      Widget widget = widgetDAO.getWidgetById(id);

      if (widget == null)
      {
         LOG.debug("Can not find widget with id = " + id);
      }
      else
      {
         String s = widget.toString();
         LOG.info(s);
      }

      List<Widget> c = widgetDAO.getWidgets();

      if (c.size() == 0)
      {
         LOG.debug("There is no record in widget table. Adding one record...");

         addRecordNoSessionManagement(widgetDAO);

         c = widgetDAO.getWidgets();
      }

      LOG.debug("Number of records in widget table: " + c.size());

      for (Widget w : c)
      {
         String s = w.toString();
         LOG.info(s);
      }
   }
}
