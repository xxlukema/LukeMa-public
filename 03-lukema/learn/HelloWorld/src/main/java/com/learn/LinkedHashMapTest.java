package com.learn;


import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;


public class LinkedHashMapTest {
    private static final Logger LOG = Logger.getLogger(LinkedHashMapTest.class);

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test.");

        

        Map<String, String> map = new LinkedHashMap<>();
        map.put("1", "one");
        map.put("3", "three");
        map.put("2", "two");
        map.put("3", "four");

        LOG.debug(map);
        
        for(String key: map.keySet()) {
            LOG.debug(key + "=" + map.get(key));
        }

        LOG.info("End Test.");
    }
}
