package com.learn.test;


import org.junit.Test;

import com.learn.util.MyLog4j1Wrapper;


public class Log4j1WrapperTest {

    private static final MyLog4j1Wrapper LOG = MyLog4j1Wrapper.getLogger();

    @Test
    public void testLog() {

        LOG.info("Begin Test");

        LOG.debug("Debug line");

        LOG.trace(() -> "Trace You" + speaker("%%%%%%%%%%% Trace me."));

        LOG.debug(() -> "You" + speaker("And me."));

        LOG.trace("Trace You" + speaker("+++++++++++ Trace me."));

        /**
         * Lambda expression does not work for log4j-1.2.17.jar 
         */
        // LOG.info(() -> "Test Provider.");

        LOG.info("End Test.");

    }

    private String speaker(String msg) {
        msg = " ============= Hi " + msg;
        System.out.println(msg);
        return msg;
    }

}
