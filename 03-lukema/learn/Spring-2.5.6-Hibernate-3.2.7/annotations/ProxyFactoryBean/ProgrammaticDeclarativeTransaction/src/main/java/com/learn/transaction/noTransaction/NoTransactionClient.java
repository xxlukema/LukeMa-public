package com.learn.transaction.noTransaction;


import java.util.List;

import junit.framework.Assert;

import com.learn.ClientBase;
import com.learn.bean.Person;
import com.learn.dao.PersonDAO;
import com.learn.util.SpringBeanFactory;


public class NoTransactionClient
   extends ClientBase
{

   public static void main(String[] args)
      throws Exception
   {
      addRecord();

      testUpdateTransaction();

      queryRecords();
   }

   public static void testUpdateTransaction()
      throws Exception
   {
      try
      {
         PersonDAO personDAO = SpringBeanFactory.getBean("personDAO");

         List<Person> people = personDAO.list();
         Assert.assertTrue(people.size() > 0);

         for (Person person : people)
         {
            person.setName("Commit. Show Data.");
            personDAO.saveOrUpdate(person);
         }

         /**
          * Throws Exception will NOT cause automatic roll back.
          */
         throw new Exception();
      }
      catch (Exception e)
      {
      }
   }

}
