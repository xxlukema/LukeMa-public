package com.learn.persistence.util;


import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.learn.persistence.service.AccessService;
import com.learn.persistence.service.UserService;


public class SpringApplicationContext
{
   protected static final Logger           LOG                     = Logger.getLogger(SpringApplicationContext.class);

   private static final String             SPRING_CONFIG_FILE_NAME = "SpringBeanConfig.xml";

   private static final ApplicationContext APP_CONTEXT             = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE_NAME);

   private static final String             BeanNameAcceessService  = "accessService";

   private static final String             BeanNameUserService     = "userService";

   private static ApplicationContext getAPP_CONTEXT()
   {
      return APP_CONTEXT;
   }

   public static AccessService getAccessService()
   {
      return SpringApplicationContext.getBean(BeanNameAcceessService);
   }

   public static UserService getUserService()
   {
      return SpringApplicationContext.getBean(BeanNameUserService);
   }

   @SuppressWarnings("unchecked")
   public static <T> T getBean(String beanName)
   {
      Object object = getAPP_CONTEXT().getBean(beanName);

      return (T) object;
   }

   /**
    * 
   <context-param>
      <param-name>contextConfigLocation</param-name>

      <param-value>/WEB-INF/SpringMVC-Trader-servlet.xml</param-value>
   </context-param>

   <listener>
      <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
   </listener>

   <bean id="applicationContextProvider" class="com.learn.util.ApplicationContextProvider">
   </bean>
   
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String beanName)
    {
       LOG.debug("ApplicationContextProvider.getApplicationContext() == null ? " + (ApplicationContextProvider.getApplicationContext() == null));

       Object object = ApplicationContextProvider.getApplicationContext().getBean(beanName);

       return (T) object;
    }*/

}
