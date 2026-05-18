package com.learn;


import java.util.ArrayList;
import java.util.List;


public class Person
   extends PersonBase
{
   private static final long serialVersionUID = 0L;

   // To learn how to map a java.util.List of BLOB or simple values.
   private List<String>      stringList;

   public List<String> getStringList()
   {
      if (stringList == null)
      {
         stringList = new ArrayList<String>();
      }

      return stringList;
   }

   public void setStringList(List<String> stringList)
   {
      this.stringList = stringList;
   }

}
