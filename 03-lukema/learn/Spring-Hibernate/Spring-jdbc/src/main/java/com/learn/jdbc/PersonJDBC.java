package com.learn.jdbc;


import java.util.List;

import com.learn.bean.Person;
import com.learn.jta.PersonJTA;


public interface PersonJDBC
{
   public static final String SQL = PersonJTA.SQL;

   public List<Person> list()
      throws Exception;
}
