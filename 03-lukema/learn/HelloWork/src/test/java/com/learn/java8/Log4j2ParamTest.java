package com.learn.java8;


import java.util.concurrent.TimeUnit;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


@Named
public class Log4j2ParamTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        LOG.info(TimeUnit.SECONDS.toMillis(30));
        LOG.info(TimeUnit.MINUTES.toMillis(30));

        LOG.info("Hi. Param1: {} param2: [{}].", "one", 2);

        LOG.trace("NOT CALLED: Log message: {}.", () -> notCalled("Not called StringFormatter lambda trace"));

        LOG.trace("Called but not printed: Log message: {}.", called("1. called StringFormatter old trace"));

        LOG.trace(() -> "Not printed: Hello world. " + notCalled("Not called lambda trace"));

        LOG.trace("Not printed: Hello world. " + called("2. called String + old trace"));

        LOG.debug("3. Called: Log message: {}.", () -> called("### lambda debug"));
        
        LOG.debug(() -> "4. Called: Hello world. " + called("--- lambda debug"));

        LOG.info("End Test.");

    }

    public String called(String str) {
        System.out.println("called() called " + str);

        return "called() " + str;
    }

    public String notCalled(String str) {
        System.out.println("notCalled() called " + str);

        return "notCalled() " + str;
    }
}
