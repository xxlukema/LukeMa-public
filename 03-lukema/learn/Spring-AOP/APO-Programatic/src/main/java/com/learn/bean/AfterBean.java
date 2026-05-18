package com.learn.bean;


import org.apache.log4j.Logger;


public class AfterBean
{
   private static final Logger LOG = Logger.getLogger(AfterBean.class);

   private String name;
   
   public String getName()
   {
      LOG.info("AfterBean.getName");
      
      return name;
   }

   public void setName(String value)
   {
      LOG.info("AfterBean.setName");
      
      name = value;
   }
}
