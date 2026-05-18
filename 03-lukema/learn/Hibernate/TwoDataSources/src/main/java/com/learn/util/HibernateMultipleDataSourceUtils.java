package com.learn.util;


import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;

import com.learn.bean.BeanBase;
import com.learn.bean.mysql.MySQLObject;
import com.learn.bean.oracle.OracleObject;
import com.learn.hibernate.HibernateUtils;


public class HibernateMultipleDataSourceUtils
{
   private static final String         HIBERNATE_CFG_ORACLE = "hibernate-oracle.cfg.xml";

   private static final String         HIBERNATE_CFG_MYSQL  = "hibernate-mysql.cfg.xml";

   private static final SessionFactory SESSION_FACTORY_ORACLE;

   private static final SessionFactory SESSION_FACTORY_MYSQL;

   private static final Logger         LOG                  = Logger.getLogger(HibernateMultipleDataSourceUtils.class);

   static
   {
      try
      {
         AnnotationConfiguration annotationConfiguration = new AnnotationConfiguration();
         annotationConfiguration.configure(HIBERNATE_CFG_ORACLE);
         SESSION_FACTORY_ORACLE = annotationConfiguration.buildSessionFactory();
      }
      catch (Throwable ex)
      {
         LOG.error("Exception with Oracle Configuration or SessionFactory", ex);
         throw new ExceptionInInitializerError(ex);
      }

      try
      {
         AnnotationConfiguration annotationConfiguration = new AnnotationConfiguration();
         annotationConfiguration.configure(HIBERNATE_CFG_MYSQL);
         SESSION_FACTORY_MYSQL = annotationConfiguration.buildSessionFactory();
      }
      catch (Throwable ex)
      {
         LOG.error("Exception with MySQL Configuration or SessionFactory", ex);
         throw new ExceptionInInitializerError(ex);
      }
   }

   private HibernateMultipleDataSourceUtils()
   {
   }

   public static <T extends BeanBase> Session openSession(Class<T> clazz)
      throws HibernateException
   {
      if (MySQLObject.class.isAssignableFrom(clazz))
      {
         return openSessionMySQL();
      }
      else if (OracleObject.class.isAssignableFrom(clazz))
      {
         return openSessionOracle();
      }
      else
      {
         throw new HibernateException("Object class not belong to the configured session factories: " + clazz.getName());
      }
   }

   private static Session openSessionOracle()
      throws HibernateException
   {
      return SESSION_FACTORY_ORACLE.openSession();
   }

   private static Session openSessionMySQL()
      throws HibernateException
   {
      return SESSION_FACTORY_MYSQL.openSession();
   }

   public static <T extends BeanBase> Serializable save(T object)
      throws Exception
   {
      Session session = null;
      Transaction transaction = null;

      try
      {
         session = openSession(object.getClass());
         transaction = session.beginTransaction();

         Serializable id = save(session, object);

         transaction.commit();

         LOG.info("Record saved. Transaction commited. Id = " + id);

         return id;
      }
      catch (Throwable t)
      {
         if (transaction != null)
         {
            transaction.rollback();
         }

         LOG.error("Exception with transaction. Rollback", t);

         throw new Exception(t);
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   private static Serializable save(Session session, Object object)
      throws Exception
   {
      if (session == null)
      {
         throw new Exception("Session is null.");
      }

      if (object == null)
      {
         throw new Exception("Object is null.");
      }

      return session.save(object);
   }

}
