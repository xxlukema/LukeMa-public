package com.learn.util;


import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringApplicationContext
{
   private static final String             SPRING_CONFIG_FILE_NAME = "SpringBeanConfig.xml";

   private static final ApplicationContext APP_CONTEXT             = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE_NAME);

   private static ApplicationContext getAPP_CONTEXT()
   {
      return APP_CONTEXT;
   }

   @SuppressWarnings("unchecked")
   public static <T> T getBean(String beanName)
   {
      Object object = getAPP_CONTEXT().getBean(beanName);
            
      return (T) object;
   }

}
