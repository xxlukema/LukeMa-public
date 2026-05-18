package com.learn.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.learn.util.MyLog4j2Wrapper;


public class MyLog4j2WrapperTest {

    static {
        MyLog4j2Wrapper.getLogger();
    }

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testLog() {

        LOG.info("Begin Test");

        LOG.debug("Debug line");

        LOG.trace(() -> "Trace You" + speaker("+++++++++++ Trace me."));

        LOG.debug(() -> "You" + speaker("And me."));

        LOG.trace("Trace You" + speaker("+++++ printed ++++++ Trace me."));

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
