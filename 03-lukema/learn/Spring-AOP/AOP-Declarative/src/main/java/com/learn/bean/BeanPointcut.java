package com.learn.bean;


import org.apache.log4j.Logger;


public class BeanPointcut
{
   private static final Logger LOG = Logger.getLogger(BeanPointcut.class);

   public void before()
   {
      LOG.info("BeanPointcut.before");
   }
   
   public String afterReturning(String strArg, Integer intArg)
   {
      LOG.info("BeanPointcut.afterReturning");
      
      return "BeanPointcut.afterReturning strArg: " + strArg + ", intArg: " + intArg;
   }
   
   public void afterThrowing()
   throws Exception
   {
      LOG.info("BeanPointcut.afterThrowing");
      
      throw new Exception("This Exception is thrown by BeanPointcut.afterThrowing().");
   }
   
   public void after()
   {
      LOG.info("BeanPointcut.after");
   }

   public String around(Integer param)
   {
      LOG.info("BeanPointcut.around");
      
      return "Your arg is: " + param;
   }
}
