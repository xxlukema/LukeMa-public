package com.learn.bean.mysql;

import com.learn.bean.BeanBase;


public class Widget
   extends BeanBase
   implements MySQLObject
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
