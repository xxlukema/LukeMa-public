package com.learn.service.impl;


import java.util.List;

import com.learn.bean.Person;
import com.learn.dao.PersonDAO;
import com.learn.service.PersonService;


public class PersonServiceImpl
   extends CommonServiceImpl
   implements PersonService
{
   private PersonDAO personDAO;

   public PersonDAO getPersonDAO()
   {
      return personDAO;
   }

   public void setPersonDAO(PersonDAO personDAO)
   {
      this.personDAO = personDAO;
   }

   private void doUpdate(String newName)
   {
      List<Person> people = getPersonDAO().list();

      for (Person person : people)
      {
         person.setName(newName);
         getPersonDAO().saveOrUpdate(person);
      }
   }

   public void updatePersonRollback(String newName)
   {
      doUpdate(newName);

      throw new RuntimeException("This will cause Rollback");
   }

   public void updatePersonCommit(String newName)
      throws Exception
   {
      doUpdate(newName);

      throw new Exception("This will not affact transaction.");
   }
}
