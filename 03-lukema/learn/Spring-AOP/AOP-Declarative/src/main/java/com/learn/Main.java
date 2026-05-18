package com.learn;


import org.apache.log4j.Logger;

import com.learn.bean.BeanPointcut;
import com.learn.util.SpringApplicationContext;


public class Main
{
   private static final Logger LOG = Logger.getLogger(Main.class);

   public static void main(String[] args)
      throws Exception
   {
      BeanPointcut beanPointcut = SpringApplicationContext.getBean("beanPointcut");

      beanPointcut.before();

      String value = beanPointcut.afterReturning("Hello World!", 2);
      LOG.info("beanPointcut.afterReturning returned: " + value);

      try
      {
         beanPointcut.afterThrowing();
      }
      catch (Exception e)
      {
         LOG.info("beanPointcut.afterThrowing msg: " + e.getMessage());
      }

      beanPointcut.after();

      String rtn = beanPointcut.around(11);
      LOG.info("beanPointcut.around returned: " + rtn);
   }
}
