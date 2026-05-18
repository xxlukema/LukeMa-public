package com.learn.bbb;


import java.text.DecimalFormat;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


@Named
public class MemoryTest {
    private static final Logger LOG = LogManager.getLogger();
    
    private static final String NumberFormat = "#,###,###"; 

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        int mb = 1024 * 1024;

        //Getting the runtime reference from system
        Runtime runtime = Runtime.getRuntime();

        LOG.info("##### Heap utilization statistics [MB] #####");

        //Print used memory
        LOG.info("Used Memory:" + (runtime.totalMemory() - runtime.freeMemory()) / mb);

        //Print free memory
        LOG.info("Free Memory:" + runtime.freeMemory() / mb);

        //Print total available memory
        LOG.info("Total Memory:" + runtime.totalMemory() / mb);

        //Print Maximum available memory
        LOG.info("Max Memory:" + runtime.maxMemory() / mb);
        
        DecimalFormat decimalFormat = new DecimalFormat(NumberFormat);
        
        String str = decimalFormat.format(runtime.maxMemory() / mb);
        
        LOG.info("Formatted: " + str);

        LOG.info("End Test.");

    }
}
