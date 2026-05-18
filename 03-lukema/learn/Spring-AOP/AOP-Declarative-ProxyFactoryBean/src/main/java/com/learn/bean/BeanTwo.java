package com.learn.bean;


import org.apache.log4j.Logger;


public class BeanTwo
{
   private static final Logger LOG = Logger.getLogger(BeanTwo.class);

   private String              name;

   public String getName()
   {
      LOG.info("BeanTwo.getName");

      return name;
   }

   public void setName(String value)
   {
      LOG.info("BeanTwo.setName");

      name = value;
   }
}
