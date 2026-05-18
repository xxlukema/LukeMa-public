package com.learn.hibernate;


import java.io.Serializable;
import java.util.List;

import junit.framework.Assert;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.AnnotationConfiguration;


public class HibernateUtils
{
   private static final SessionFactory SESSION_FACTORY;

   private static final Logger         LOG = Logger.getLogger(HibernateUtils.class);

   static
   {
      try
      {
         AnnotationConfiguration annotationConfiguration = new AnnotationConfiguration();
         annotationConfiguration.configure();
         SESSION_FACTORY = annotationConfiguration.buildSessionFactory();
      }
      catch (Throwable ex)
      {
         LOG.error("Exception with Configuration or SessionFactory", ex);
         throw new ExceptionInInitializerError(ex);
      }
   }

   public static Session openSession()
      throws HibernateException
   {
      return SESSION_FACTORY.openSession();
   }

   public static Session getCurrentSession()
      throws HibernateException
   {
      return SESSION_FACTORY.getCurrentSession();
   }

   public static void delete(Session session, Object object)
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

      session.delete(object);
   }

   public static void delete(Object object)
      throws Exception
   {
      Session session = null;
      Transaction transaction = null;

      try
      {
         session = HibernateUtils.openSession();
         transaction = session.beginTransaction();

         delete(session, object);

         transaction.commit();

         LOG.info("Record deleted. Transaction commited.");
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
         close(session);
      }
   }

   public static Serializable save(Session session, Object object)
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

   public static Serializable save(Object object)
      throws Exception
   {
      Session session = null;
      Transaction transaction = null;

      try
      {
         session = HibernateUtils.openSession();
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
         close(session);
      }
   }

   public static void saveOrUpdate(Session session, Object object)
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

      session.saveOrUpdate(object);
   }

   public static void saveOrUpdate(Object object)
      throws Exception
   {
      Session session = null;
      Transaction transaction = null;

      try
      {
         session = HibernateUtils.openSession();
         transaction = session.beginTransaction();

         saveOrUpdate(session, object);

         transaction.commit();

         LOG.info("Record saved or updated. Transaction commited.");
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
         close(session);
      }
   }

   public static void close(Session session)
   {
      if (session != null)
      {
         try
         {
            if (session.isOpen())
            {
               session.close();
            }
         }
         catch (Throwable t)
         {
            LOG.error("Exception closing Hibernate Session.", t);
         }
      }
   }

   public static void close(SessionFactory sessionFactory)
   {
      if (sessionFactory != null)
      {
         try
         {
            sessionFactory.close();
         }
         catch (Throwable t)
         {
            LOG.error("Exception closing Hibernate SessionFactory.", t);
         }
      }
   }

   public static <T> List<T> list(Session session, Class<T> clazz)
      throws Exception
   {
      Criteria criteria = session.createCriteria(clazz);

      @SuppressWarnings("unchecked")
      List<T> list = criteria.list();

      return list;
   }

   /**
    * This allows lazy association. 
    */
   public static <T> void sampleList(Class<T> clazz)
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         List<T> list = HibernateUtils.list(session, clazz);
         Assert.assertTrue(list.size() > 0);

         for (T t : list)
         {
            LOG.info("T class name: " + t.getClass().getName());
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }
}
