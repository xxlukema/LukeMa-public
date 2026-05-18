package com.learn.dao;


import java.util.List;

import com.learn.bean.Person;


public interface PersonDAO
   extends CommonDAO
{
   public List<Person> list()
      throws Exception;
}
