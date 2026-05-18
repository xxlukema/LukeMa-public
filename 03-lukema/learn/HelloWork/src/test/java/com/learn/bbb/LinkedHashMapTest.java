package com.learn.bbb;


import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.jupiter.api.Test;


public class LinkedHashMapTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test.");

        

        Map<String, String> map = new LinkedHashMap<String, String>();
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
