package com.learn.test;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.lifecycle.MyBean;
import com.learn.lifecycle.MyConfig;


@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:LifeCycleConfig.xml" })
@ContextConfiguration(classes = MyConfig.class)
public class LifeCycleTest {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private MyBean myBean1;

    @Autowired
    private MyBean myBean2;

    @Test
    public void testAutowired() {
        LOG.info("Begin Test.");
        
        myBean1.sayHello();
        myBean2.sayHello();
        
        LOG.info("End Test.");
    }

}
