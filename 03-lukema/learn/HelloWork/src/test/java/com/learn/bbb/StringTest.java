package com.learn.bbb;


import java.util.Arrays;
import java.util.List;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


@Named
public class StringTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        String str = "boo:and:foo";

        String[] fields = str.split("o");
        List<String> list = Arrays.asList(fields);

        LOG.info(list);

        /*fields = str.split("o", 10);
        list = Arrays.asList(fields);
        
        LOG.info(list);
        
        fields = str.split("o", 0);
        list = Arrays.asList(fields);
        
        LOG.info(list);
        
        fields = str.split("o", -1);
        list = Arrays.asList(fields);
        
        LOG.info(list);*/

        str = "boo :  and:          foo ";

        fields = str.split("\\s*:\\s*");
        // fields = str.split(":");
        list = Arrays.asList(fields);

        LOG.info(list);

        LOG.info("End Test.");

    }
}
