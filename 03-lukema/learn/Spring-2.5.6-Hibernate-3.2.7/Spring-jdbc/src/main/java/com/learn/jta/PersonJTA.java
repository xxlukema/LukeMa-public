package com.learn.jta;

import java.util.List;

import com.learn.bean.Person;


public interface PersonJTA
{
   public static final String SQL = "select id, name, weight from people where id > :id";
   
   public List<Person> list()
      throws Exception;
}

