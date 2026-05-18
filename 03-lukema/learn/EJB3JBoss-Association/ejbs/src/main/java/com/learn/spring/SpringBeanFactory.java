package com.learn.spring;


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringBeanFactory
   implements ObjectFactory
{
   private static final String             SPRING_CONFIG_FILE_NAME = "SpringBeanConfig.xml";

   private static final ApplicationContext APP_CONTEXT             = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE_NAME);

   @Override
   public Object getObjectInstance(Object reference, Name name, Context nameCtx, Hashtable<?, ?> environment)
      throws Exception
   {
      String beanName = (String) ((Reference) reference).get(0).getContent();
      return APP_CONTEXT.getBean(beanName, Object.class);
   }

   @SuppressWarnings("unchecked")
   public static <T> T getBean(String beanName)
   {
      return (T) APP_CONTEXT.getBean(beanName);
   }

}
