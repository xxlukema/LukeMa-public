package com.learn.transaction.callback;


import java.util.List;

import junit.framework.Assert;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;

import com.learn.bean.Person;
import com.learn.dao.PersonDAO;
import com.learn.util.SpringApplicationContext;


public class TransactionCallbackCommit
   implements TransactionCallback
{
   public Object doInTransaction(TransactionStatus transactionStatus)
   {
      try
      {
         PersonDAO personDAO = SpringApplicationContext.getBean("personDAO");

         List<Person> people = personDAO.list();
         Assert.assertTrue(people.size() > 0);

         for (Person person : people)
         {
            person.setName("Commit. Show Data.");
            personDAO.saveOrUpdate(person);
         }
      }
      catch (Exception e)
      {
         transactionStatus.setRollbackOnly();
      }

      return null;
   }
}
