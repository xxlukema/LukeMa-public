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

   public List<Person> list()
      throws Exception
   {
      return getPersonDAO().list();
   }

   public void setPersonDAO(PersonDAO personDAO)
   {
      this.personDAO = personDAO;
   }

   public PersonDAO getPersonDAO()
   {
      return personDAO;
   }

}
