package com.learn.util;


import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.Reference;
import javax.naming.spi.ObjectFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


public class SpringBeanFactory
    implements ObjectFactory {
    private static final String SPRING_CONFIG_FILE_NAME = "SpringMVC-servlet.xml";

    private static final ApplicationContext APP_CONTEXT = new ClassPathXmlApplicationContext(SPRING_CONFIG_FILE_NAME);

    @Override
    public Object getObjectInstance(Object reference, Name name, Context nameCtx, Hashtable<?, ?> environment)
        throws Exception {
        String beanName = (String) ((Reference) reference).get(0).getContent();
        return APP_CONTEXT.getBean(beanName, Object.class);
    }

    public static <T> T getBean(String beanName, Class<T> type) {
        return APP_CONTEXT.getBean(beanName, type);
    }

}
