package com.learn.lifecycle;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class MyConfig {

    private static final Logger LOG = LogManager.getLogger();

    @Bean(initMethod = "init", destroyMethod = "destroy")
    @Scope("prototype")
    public MyBean myBean() {
        LOG.info("create.");
        return new MyBean();
    }

}
