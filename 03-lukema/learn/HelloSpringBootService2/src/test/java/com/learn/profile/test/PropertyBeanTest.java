package com.learn.profile.test;


import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;

import com.learn.bean.PropertyBean;
import com.learn.boot.main.HelloSpringBootMainApplication;

import lombok.extern.log4j.Log4j2;


@Log4j2
@SpringBootTest(classes = { HelloSpringBootMainApplication.class }, webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles({ "test" })
public class PropertyBeanTest {

    @Autowired
    private PropertyBean propertyBean;

    @Test
    public void testProperty() {

        log.info("Start test.");

        log.info("My age: {}", this.propertyBean.getAge());

        /**
         * # application.properties
         * # my.property.age=25
         * #
         * # application-default.properties
         * # my.property.age=18
         * #
         * my.property.age = 40
         * 
         */
        Assert.assertEquals("40", this.propertyBean.getAge());

        log.info("End test.");
    }

}
