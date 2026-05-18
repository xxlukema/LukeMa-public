package com.learn.manual;


import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.learn.hibernate.HibernateUtils;
import com.learn.manual.bean.ChildOne;
import com.learn.manual.bean.ChildOneChild;
import com.learn.manual.bean.ChildTwo;
import com.learn.manual.bean.Parent;


public class ManualDriver
{
   private static final Logger LOG = Logger.getLogger(ManualDriver.class);

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
      Parent parent1 = new Parent();
      HibernateUtils.saveOrUpdate(parent1);

      ChildOne childOne1 = new ChildOne();
      childOne1.setParentId(parent1.getId());
      HibernateUtils.saveOrUpdate(childOne1);

      ChildOne childOne2 = new ChildOne();
      childOne2.setParentId(parent1.getId());
      HibernateUtils.saveOrUpdate(childOne2);

      ChildOne childOne3 = new ChildOne();
      childOne3.setParentId(parent1.getId());
      HibernateUtils.saveOrUpdate(childOne3);

      ChildTwo childTwo1 = new ChildTwo();
      childTwo1.setParentId(parent1.getId());
      HibernateUtils.saveOrUpdate(childTwo1);

      ChildOneChild childOneChild1 = new ChildOneChild();
      childOneChild1.setParentId(childOne2.getId());
      HibernateUtils.saveOrUpdate(childOneChild1);

      Parent parent2 = new Parent();
      HibernateUtils.saveOrUpdate(parent2);

      ChildOne childOne4 = new ChildOne();
      childOne4.setParentId(parent2.getId());
      HibernateUtils.saveOrUpdate(childOne4);

      Parent parent3 = new Parent();
      HibernateUtils.saveOrUpdate(parent3);

      LOG.info("Data Initiated.");
   }

   public static void retrieveData()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("manual.parent.findAll");

         @SuppressWarnings("unchecked")
         List<Parent> parents = query.list();

         if (parents.size() > 0)
         {
            for (Parent parent : parents)
            {
               ChildTwo childTwo = findChildTwo(parent);
               parent.setChildTwo(childTwo);

               List<ChildOne> childOneChildren = findChildOnes(parent);
               parent.setChildOneChildren(childOneChildren);

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

   public static ChildOneChild findChildOneChild(ChildOne childOne)
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("manual.childOneChild.findByParentId");
         query.setLong("parentId", childOne.getId());

         ChildOneChild childOneChild = (ChildOneChild) query.uniqueResult();
         if (childOneChild != null)
         {
            childOneChild.setParent(childOne);
         }

         return childOneChild;
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static List<ChildOne> findChildOnes(Parent parent)
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("manual.childOne.findByParentId");
         query.setLong("parentId", parent.getId());

         @SuppressWarnings("unchecked")
         List<ChildOne> childOneChildren = query.list();
         ;
         for (ChildOne childOne : childOneChildren)
         {
            ChildOneChild childOneChild = findChildOneChild(childOne);
            childOne.setChild(childOneChild);
            childOne.setParent(parent);
         }

         return childOneChildren;
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static ChildTwo findChildTwo(Parent parent)
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("manual.childTwo.findByParentId");
         query.setLong("parentId", parent.getId());

         ChildTwo childTwo = (ChildTwo) query.uniqueResult();
         if (childTwo != null)
         {
            childTwo.setParent(parent);
         }

         return childTwo;
      }
      finally
      {
         HibernateUtils.close(session);
      }
   }

   public static void retrieveData_Like()
      throws Exception
   {
      Session session = null;

      try
      {
         session = HibernateUtils.openSession();
         Query query = session.getNamedQuery("manual.parent.like");
         query.setString("key", "%1");

         @SuppressWarnings("unchecked")
         List<Parent> parents = query.list();

         if (parents.size() > 0)
         {
            for (Parent parent : parents)
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
}
