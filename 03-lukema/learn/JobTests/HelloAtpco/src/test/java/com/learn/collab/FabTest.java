package com.learn.collab;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;;


public class FabTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testFab() {

        LOG.info("Start Test.");

        FabNumberGenerator fabNumberGenerator = new FabNumberGenerator();

        fabNumberGenerator.startGen();

        LOG.info("End Test.");

    }

    @Test
    public void testGenNext() {

        LOG.info("Start Test.");

        FabNumberGenerator fabNumberGenerator = new FabNumberGenerator();

        int seed = 23;
        int next = 50;

        int generated = fabNumberGenerator.genNext(seed, next);

        Assert.assertEquals("genNext", (seed + next), generated);

        LOG.info("End Test.");

    }

    @Test(expected = Exception.class)
    public void testGenNextException()
        throws Exception {

        LOG.info("Start Test.");

        FabNumberGenerator fabNumberGenerator = new FabNumberGenerator();

        int seed = 23;
        int next = 150;

        int generated = fabNumberGenerator.genNextException(seed, next);
        Assert.fail("It should not get here.");

        LOG.info("End Test.");

    }

}
