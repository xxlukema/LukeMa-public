package com.learn.properties.test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.learn.bean.PropertyBean;
import com.learn.boot.config.BootJpaConfig;
import com.learn.boot.config.BootSecurityConfig;
import com.learn.boot.config.MyProperties;

import lombok.extern.log4j.Log4j2;


/**
 * mvn test -Dtest=PropertiesTest
 */
@Log4j2
@ContextConfiguration(classes = { BootJpaConfig.class, BootSecurityConfig.class })
@SpringBootTest
class PropertiesTest {

    @Autowired
    private MyProperties myProperties;

    @Autowired
    private PropertyBean propertyBean;

    @Test
    void testMyPropertiesAge() {
        log.debug(() -> "Start");

        var age = myProperties.getAge();
        log.debug("age: {}", () -> age);

        /**
         * (1) Explicitly specified `@PropertySource("classpath:my.properties")`
         * (2) The last line that assigns the value in `src/test/resources/my.properties` will win.
         */
        assertEquals(127, age);

        log.debug(() -> "End");
    }

    @Test
    void testAge() {
        log.debug(() -> "Start");

        var age = propertyBean.getAge();
        log.debug("age: {}", () -> age);

        /**
         * (1) Get the value from `src/main/resources/application.properties` for `mvn spring-boot:run`
         * (2) Get the value from `src/test/resources/application-default.properties` for `mvn test`.
         * (3) `src/main/resources/application.properties` will be ignored for `mvn test` if this file exists: `src/test/resources/application.properties`.
         * (4) A property in `src/main/resources/application.properties` can be redefined in that file, but `VSCode PROBLEMS` will display warn.
         */
        assertEquals(18, age);

        log.debug(() -> "End");
    }

}
