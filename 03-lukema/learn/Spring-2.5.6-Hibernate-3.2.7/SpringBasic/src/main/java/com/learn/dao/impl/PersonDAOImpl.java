package com.learn.dao.impl;


import java.util.List;

import com.learn.bean.Person;
import com.learn.dao.PersonDAO;
import com.learn.dao.impl.CommonDAOImpl;


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
