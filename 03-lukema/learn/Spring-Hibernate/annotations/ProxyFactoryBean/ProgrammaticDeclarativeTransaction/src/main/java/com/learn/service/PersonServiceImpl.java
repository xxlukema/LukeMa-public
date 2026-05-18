package com.learn.service;


import java.util.List;

import com.learn.bean.Person;
import com.learn.dao.PersonDAO;


public class PersonServiceImpl
   implements PersonService
{
   private PersonDAO personDAO;

   public PersonDAO getPersonDAO()
   {
      return personDAO;
   }

   /**
    * @see PersonService#setPersonDAO(PersonDAO)
    */
   public void setPersonDAO(PersonDAO personDAO)
   {
      this.personDAO = personDAO;
   }

   private void doUpdate(String newName)
      throws Exception
   {
      PersonDAO personDAO = getPersonDAO();

      List<Person> people = personDAO.list();

      for (Person person : people)
      {
         person.setName(newName);
         personDAO.saveOrUpdate(person);
      }
   }
   
   /**
    * @see PersonService#updatePersonRollback()
    */
   public void updatePersonRollback() throws Exception
   {
      doUpdate("Rollback. Not Show.");

      throw new AppException("This will cause Rollback");
   }
   
   /**
    * @see PersonService#updatePersonCommit()
    */
   public void updatePersonCommit() throws Exception
   {
      doUpdate("Commit. Show New Name.");
      
      throw new Exception("This will not affact transaction.");
   }
}
