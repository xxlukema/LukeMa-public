package com.learn.oracle.dao;


import java.util.List;

import com.learn.bean.oracle.OracleObject;
import com.learn.bean.oracle.Person;
import com.learn.common.dao.CommonDAO;


public interface PersonDAO
   extends CommonDAO
{
   public List<Person> list()
      throws Exception;
   
   public <T extends OracleObject> T saveOrUpdate(T bean)
   throws Exception;
}
