package com.learn.bean.oracle;

import com.learn.bean.BeanBase;


public class Person
   extends BeanBase
   implements OracleObject
{
   private static final long serialVersionUID = 0L;

   private String            name;

   public String getName()
   {
      return name;
   }

   public void setName(String name)
   {
      this.name = name;
   }
}
