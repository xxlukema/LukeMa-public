package com.learn.util;


import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;


public class SpringBeanFactory
{
   private static final String      SPRING_CONFIG_FILE_NAME = "SpringBeanConfig.xml";

   private static final Resource    RESOURCE                = new ClassPathResource(SPRING_CONFIG_FILE_NAME);

   private static final BeanFactory BEAN_FACTORY            = new XmlBeanFactory(RESOURCE);

   private static BeanFactory getBEAN_FACTORY()
   {
      return BEAN_FACTORY;
   }

   @SuppressWarnings("unchecked")
   public static <T> T getBean(String beanName)
   {
      return (T) getBEAN_FACTORY().getBean(beanName);
   }

}
