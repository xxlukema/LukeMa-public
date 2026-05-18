package com.learn.bbb;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;


public class LombokTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testMain()
        throws Exception {

        LOG.info("Begin Test.");

        // Test
        MyLombok myLombok = new MyLombok();
        myLombok.setAge(10);
        myLombok.setName("Luke");
        LOG.info(() -> myLombok);
        myLombok.doLog();

        LOG.info("End Test.");

    }
}


@Log4j2
@ToString
@Getter
@Setter
class MyLombok {
    private String name;
    private int age;

    public void doLog() {
        log.debug(() -> "Test logger.");
    }
}
