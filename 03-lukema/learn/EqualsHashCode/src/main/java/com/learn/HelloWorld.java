package com.learn;


import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);


   public static void main(String [] args)
   {
      LOG.info("Hello World!");
      
      Set<CascadingRequestEntityBean> set = new HashSet<CascadingRequestEntityBean>();
      
      CascadingRequestEntityBean cascadingRequestEntityBean1 = new CascadingRequestEntityBean();
      cascadingRequestEntityBean1.setSsn("ssn");
      cascadingRequestEntityBean1.setAge(20L);
      cascadingRequestEntityBean1.setStatus("Active");
      
      CascadingRequestEntityBean cascadingRequestEntityBean2 = new CascadingRequestEntityBean();
      cascadingRequestEntityBean2.setSsn("ssn");
      cascadingRequestEntityBean2.setAge(20L);
      cascadingRequestEntityBean2.setStatus("InActive");

      CascadingRequestEntityBean cascadingRequestEntityBean3 = new CascadingRequestEntityBean();
      cascadingRequestEntityBean3.setSsn("ssn");
      cascadingRequestEntityBean3.setAge(18L);
      cascadingRequestEntityBean3.setStatus("Active");

      set.add(cascadingRequestEntityBean1);
      set.add(cascadingRequestEntityBean2);
      set.add(cascadingRequestEntityBean3);

      System.out.println("Size of the set: " + set.size());
      
      for(CascadingRequestEntityBean bean : set)
      {
    	  System.out.println("-----------------------");
    	  System.out.println("ssn: " + bean.getSsn());
    	  System.out.println("age: " + bean.getAge());
    	  System.out.println("status: " + bean.getStatus());
    	  System.out.println("");
      }
      
      if(set.contains(cascadingRequestEntityBean2))
      {
    	  set.remove(cascadingRequestEntityBean2);
    	  set.add(cascadingRequestEntityBean2);
      }
      
      System.out.println("Size of the set: " + set.size());
      
      for(CascadingRequestEntityBean bean : set)
      {
    	  System.out.println("-----------------------");
    	  System.out.println("ssn: " + bean.getSsn());
    	  System.out.println("age: " + bean.getAge());
    	  System.out.println("status: " + bean.getStatus());
    	  System.out.println("");
      }

   }
}
