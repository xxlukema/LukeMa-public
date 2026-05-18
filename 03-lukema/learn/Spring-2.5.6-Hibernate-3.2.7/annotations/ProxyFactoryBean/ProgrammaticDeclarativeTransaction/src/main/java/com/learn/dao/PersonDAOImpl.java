package com.learn.dao;


import java.util.List;

import com.learn.bean.Person;


public class PersonDAOImpl
   extends CommonDAOImpl
   implements PersonDAO
{
   public List<Person> list()
      throws Exception
   {
      return super.list(Person.class);
   }
}
