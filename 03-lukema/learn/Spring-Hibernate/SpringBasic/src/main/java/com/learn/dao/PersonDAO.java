package com.learn.dao;


import java.util.List;

import com.learn.bean.Person;
import com.learn.dao.CommonDAO;


public interface PersonDAO
   extends CommonDAO
{
   public List<Person> list()
      throws Exception;
}
