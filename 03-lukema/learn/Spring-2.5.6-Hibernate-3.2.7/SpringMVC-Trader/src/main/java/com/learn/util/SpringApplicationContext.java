package com.learn.util;


import org.apache.log4j.Logger;


public class SpringApplicationContext
{
   protected static final Logger LOG = Logger.getLogger(SpringApplicationContext.class);

   /* private static final String             SPRING_CONFIG_FILE_NAME = "SpringBeanConfig.xml";

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
    }*/

   @SuppressWarnings("unchecked")
   public static <T> T getBean(String beanName)
   {
      LOG.info("ApplicationContextProvider.getApplicationContext() == null ? " + (ApplicationContextProvider.getApplicationContext() == null));

      Object object = ApplicationContextProvider.getApplicationContext().getBean(beanName);

      return (T) object;
   }

}
