package com.learn.dao;


import java.util.List;

import com.learn.bean.Person;
import com.learn.dao.CommonDAOImpl;


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
