package com.learn;


import java.util.Collection;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Assert;

import com.learn.bean.Child;
import com.learn.bean.Parent;
import com.learn.hibernate.HibernateUtils;


public class OneToOneMainBase
{
   private static final Logger LOG = Logger.getLogger(OneToOneMainBase.class);

   public static void deleteParents()
      throws Exception
   {
      Session session = null;
      Transaction transaction = null;

      try
      {
         session = HibernateUtils.openSession();
         transaction = session.beginTransaction();

         Criteria criteria = session.createCriteria(Parent.class);

         @SuppressWarnings("unchecked")
         Collection<Parent> parents = criteria.list();
         Assert.assertTrue(parents.size() > 0);

         for (Parent parent : parents)
         {
            HibernateUtils.delete(session, parent);
            LOG.info("Parent deleted: " + parent.getName());
         }

         transaction.commit();
      }
      catch (Throwable t)
      {
         if (transaction != null)
         {
            transaction.rollback();
         }

         LOG.error("Exception with transaction. Rollback: " + t.getMessage());

         throw new Exception(t);
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void deleteChildren()
      throws Exception
   {
      Session session = null;
      Transaction transaction = null;

      try
      {
         session = HibernateUtils.openSession();
         transaction = session.beginTransaction();

         Criteria criteria = session.createCriteria(Child.class);

         @SuppressWarnings("unchecked")
         List<Child> children = criteria.list();
         Assert.assertTrue(children.size() > 0);

         for (Child child : children)
         {
            HibernateUtils.delete(session, child);
            LOG.info("Child deleted: " + child.getName());
         }

         transaction.commit();
      }
      catch (Throwable t)
      {
         if (transaction != null)
         {
            transaction.rollback();
         }

         LOG.error("Exception with transaction. Rollback: " + t.getMessage());

         throw new Exception(t);
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void retrieveParents()
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Criteria criteria = session.createCriteria(Parent.class);

         @SuppressWarnings("unchecked")
         List<Parent> parents = criteria.list();
         LOG.info("Number of parents: " + parents.size());

         for (Parent parent : parents)
         {
            String parentName = parent.getName();
            LOG.info("Parent name: " + parentName);

            Child child = parent.getChild();
            if (child == null)
            {
               LOG.info("Parent name: " + parentName + " has no child.");
            }
            else
            {
               String childName = child.getName();
               LOG.info("Parent name: " + parentName + " \tChild name: " + childName);
            }
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void retrieveChildren()
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Criteria criteria = session.createCriteria(Child.class);

         @SuppressWarnings("unchecked")
         List<Child> children = criteria.list();
         LOG.info("Number of children: " + children.size());

         for (Child child : children)
         {
            String childName = child.getName();
            LOG.info("Child name: " + childName);

            Parent parent = child.getParent();
            if (parent == null)
            {
               LOG.info(childName + " is an orphan.");
            }
            else
            {
               LOG.info(childName + "'s parent is " + parent.getName());
            }
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }
}
