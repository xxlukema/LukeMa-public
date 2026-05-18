package com.learn.amzn;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class ArraysSort {

    private static final Logger LOG = LogManager.getLogger();

    public void test() {
        LOG.info("Test begin.");
        LOG.debug("www");
    }
    
    @Test
    public void testSort() {
        LOG.info("Test begin.");
        
        ArraysSort arraysSort = new ArraysSort();
        arraysSort.test();

        LOG.info("Test complete.");
    }
}
