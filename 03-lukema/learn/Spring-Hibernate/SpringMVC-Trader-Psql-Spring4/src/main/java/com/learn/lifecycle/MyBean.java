package com.learn.lifecycle;


import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Scope;
import org.springframework.context.weaving.LoadTimeWeaverAware;
import org.springframework.core.io.ResourceLoader;
import org.springframework.instrument.classloading.LoadTimeWeaver;
import org.springframework.jmx.export.notification.NotificationPublisher;
import org.springframework.jmx.export.notification.NotificationPublisherAware;
import org.springframework.stereotype.Component;


@Component
//@Bean(initMethod = "init", destroyMethod = "destroy")
@Scope("prototype")
//@Scope("singleton")
public class MyBean
    implements InitializingBean, ApplicationContextAware, ApplicationEventPublisherAware, BeanClassLoaderAware, BeanFactoryAware, BeanNameAware, LoadTimeWeaverAware,
    MessageSourceAware, NotificationPublisherAware, ResourceLoaderAware {

    private static final Logger LOG = LogManager.getLogger();

    private int myInt;

    @PostConstruct
    public void postConstruct() {
        LOG.debug("postConstruct");
    }

    @PreDestroy
    protected void preDestroy() {
        LOG.debug("preDestroy");
    }

    public void sayHello() {
        LOG.info("Hello from MyBean.");
    }

    protected void finalize() {
        LOG.info("finalize");
    }

    public void init() {
        LOG.info("init");
    }

    public void destroy() {
        LOG.info("destroy");
    }

    @Override
    public void afterPropertiesSet() {
        LOG.info("afterPropertiesSet");
    }

    public int getMyInt() {
        LOG.info("getMyInt");
        return myInt;
    }

    public void setMyInt(int myInt) {
        LOG.info("setMyInt");
        this.myInt = myInt;
    }

    @Override
    public void setResourceLoader(ResourceLoader arg0) {
        LOG.info("setResourceLoader");
    }

    @Override
    public void setNotificationPublisher(NotificationPublisher arg0) {
        LOG.info("setNotificationPublisher");
    }

    @Override
    public void setMessageSource(MessageSource arg0) {
        LOG.info("setMessageSource");
    }

    @Override
    public void setLoadTimeWeaver(LoadTimeWeaver arg0) {
        LOG.info("setLoadTimeWeaver");
    }

    @Override
    public void setBeanName(String arg0) {
        LOG.info("setBeanName");
    }

    @Override
    public void setBeanFactory(BeanFactory arg0)
        throws BeansException {
        LOG.info("setBeanFactory");
    }

    @Override
    public void setBeanClassLoader(ClassLoader arg0) {
        LOG.info("setBeanClassLoader");
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher arg0) {
        LOG.info("setApplicationEventPublisher");
    }

    @Override
    public void setApplicationContext(ApplicationContext arg0)
        throws BeansException {
        LOG.info("setApplicationContext");
    }
}
