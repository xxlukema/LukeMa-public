package com.learn.auto;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.learn.auto.bean.AutoChildOne;
import com.learn.auto.bean.AutoChildOneChild;
import com.learn.auto.bean.AutoChildTwo;
import com.learn.auto.bean.AutoParent;
import com.learn.hibernate.HibernateUtils;


public class AutoDriver
{
   private static final Logger LOG = Logger.getLogger(AutoDriver.class);

   public static void main(String[] args)
      throws Exception
   {
      addRecord();

      retrieveData();

      retrieveData_Like();
   }

   public static void addRecord()
      throws Exception
   {
      // Parent1
      AutoParent parent1 = new AutoParent();
      HibernateUtils.saveOrUpdate(parent1);

      AutoChildOne childOne1 = new AutoChildOne();
      childOne1.setParent(parent1);
      HibernateUtils.saveOrUpdate(childOne1);

      AutoChildOne childOne2 = new AutoChildOne();
      childOne2.setParent(parent1);
      HibernateUtils.saveOrUpdate(childOne2);

      AutoChildOne childOne3 = new AutoChildOne();
      childOne3.setParent(parent1);
      HibernateUtils.saveOrUpdate(childOne3);

      AutoChildTwo childTwo1 = new AutoChildTwo();
      childTwo1.setParent(parent1);
      HibernateUtils.saveOrUpdate(childTwo1);

      AutoChildOneChild childOneChild1 = new AutoChildOneChild();
      childOneChild1.setParent(childOne2);
      HibernateUtils.saveOrUpdate(childOneChild1);

      AutoParent parent2 = new AutoParent();
      HibernateUtils.saveOrUpdate(parent2);

      AutoChildOne childOne4 = new AutoChildOne();
      childOne4.setParent(parent2);
      HibernateUtils.saveOrUpdate(childOne4);

      AutoParent parent3 = new AutoParent();
      HibernateUtils.saveOrUpdate(parent3);

      LOG.info("Data Initiated.");
   }

   public static void retrieveData()
      throws Exception
   {
      LOG.info("Inside retrieveData()");

      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("auto.parent.findAll");

         @SuppressWarnings("unchecked")
         List<AutoParent> parents = query.list();

         if (parents.size() > 0)
         {
            for (AutoParent parent : parents)
            {
               parent.print();
            }
         }
         else
         {
            LOG.error("No Parent found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void retrieveData_Like()
      throws Exception
   {
      LOG.info("Inside retrieveData_Like()");

      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("auto.parent.like");
         query.setString("key", "%1");

         @SuppressWarnings("unchecked")
         List<AutoParent> parents = query.list();

         if (parents.size() > 0)
         {
            for (AutoParent parent : parents)
            {
               parent.print();
            }
         }
         else
         {
            LOG.error("No Parent found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void selectAutoChildTwo()
      throws Exception
   {
      LOG.info("Inside selectAutoChildTwo()");

      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         String hql = "from AutoParent where childTwo.name = 'AutoChildTwo 1' ";
         Query query = session.createQuery(hql);

         @SuppressWarnings("unchecked")
         List<AutoParent> parents = query.list();

         if (parents.size() > 0)
         {
            for (AutoParent parent : parents)
            {
               parent.print();
            }
         }
         else
         {
            LOG.error("No Parent found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void selectForAutoChildOneChild()
      throws Exception
   {
      LOG.info("Inside selectForAutoChildOneChild()");

      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         String hql = "select autoParent from AutoParent autoParent, AutoChildOneChild autoChildOneChild where autoChildOneChild in elements(autoParent.childOneChildren) and autoChildOneChild.name = 'AutoChildOneChild 1' ";
         Query query = session.createQuery(hql);

         @SuppressWarnings("unchecked")
         List<AutoParent> parents = query.list();

         if (parents.size() > 0)
         {
            for (AutoParent parent : parents)
            {
               parent.print();
            }
         }
         else
         {
            LOG.error("No Parent found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void selectForAutoChildOneChild_Criteria()
      throws Exception
   {
      LOG.info("Inside selectForAutoChildOneChild_Criteria()");

      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         Criteria criteria = session.createCriteria(AutoChildOneChild.class);
         criteria.add(Restrictions.eq("name", "AutoChildOneChild 1"));

         AutoChildOneChild autoChildOneChild = (AutoChildOneChild) criteria.uniqueResult();

         if (autoChildOneChild != null)
         {
            AutoParent parent = autoChildOneChild.getParent().getParent();
            parent.print();
         }
         else
         {
            LOG.error("No Parent found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void selectAutoChildOne()
      throws Exception
   {
      LOG.info("Inside selectAutoChildOne()");

      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         String hql = "from AutoChildOne where child.name = 'AutoChildOneChild 1' ";
         Query query = session.createQuery(hql);

         @SuppressWarnings("unchecked")
         List<AutoChildOne> autoChildOnes = query.list();

         if (autoChildOnes.size() > 0)
         {
            for (AutoChildOne autoChildOne : autoChildOnes)
            {
               LOG.info("autoChildOne.print()");
               autoChildOne.print();

               AutoParent parent = autoChildOne.getParent();
               LOG.info("parent.setThatChildOne(autoChildOne)");
               parent.setThatChildOne(autoChildOne);
               LOG.info("Parent name: " + parent.getName() + ". thatChildOne Name: " + parent.getThatChildOne().getName());

               LOG.info("parent.print()");
               parent.print();
            }
         }
         else
         {
            LOG.error("No Parent found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void selectAutoParent()
      throws Exception
   {
      LOG.info("Inside selectAutoParent()");

      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         String hql = "select autoParent from AutoParent autoParent join autoParent.childOneChildren child where child.child.name like 'AutoChildOneChild %' ";
         Query query = session.createQuery(hql);

         @SuppressWarnings("unchecked")
         List<AutoParent> autoParents = query.list();

         if (autoParents.size() > 0)
         {
            for (AutoParent autoParent : autoParents)
            {
               autoParent.print();
            }
         }
         else
         {
            LOG.error("No Parent found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void selectAutoParent_Criteria()
      throws Exception
   {
      LOG.info("Inside selectAutoParent_Criteria()");

      Session session = null;

      try
      {
         session = HibernateUtils.openSession();

         Criteria criteria = session.createCriteria(AutoParent.class);
         criteria.createAlias("childOneChildren", "childOne");
         criteria.createAlias("childOne.child", "child");
         criteria.add(Restrictions.like("child.name", "AutoChildOneChild %"));

         @SuppressWarnings("unchecked")
         List<AutoParent> autoParents = criteria.list();

         if (autoParents.size() > 0)
         {
            for (AutoParent autoParent : autoParents)
            {
               autoParent.print();
            }
         }
         else
         {
            LOG.error("No Parent found.");
         }
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }
}
