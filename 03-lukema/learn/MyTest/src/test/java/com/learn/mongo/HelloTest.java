package com.learn.mongo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class HelloTest {
    private static final Logger LOG = LogManager.getLogger();

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");
    }

    @After
    public void after()
        throws Exception {
        LOG.info("after(). For each test.");
    }

    @Test
    public void testListDatabaseNames()
        throws Exception {
        LOG.info("Begin Test");

     

        LOG.info("End Test.");
    }
}
