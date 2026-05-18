package com.learn;


import java.util.Set;
import java.util.TimeZone;
import java.util.TreeSet;

import org.apache.log4j.Logger;


public class HelloWorld
{
   protected static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
      throws Exception
   {
      LOG.info("Hello World!");

      String[] ids = TimeZone.getAvailableIDs();

      Set<String> set = new TreeSet<String>();
      
      for (String id : ids)
      {
         set.add(id);
      }
      
      for (String id : set)
      {
         System.out.println(id);
      }

      LOG.info("Deafult TimeZone Id: " + TimeZone.getDefault().getID());
   }
}
