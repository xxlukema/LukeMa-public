package com.learn.service;


import java.util.List;

import com.learn.bean.Person;


public interface PersonService
   extends CommonService
{
   public List<Person> list()
      throws Exception;
}
