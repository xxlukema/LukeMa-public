package com.learn;


import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.log4j.Logger;
import org.junit.Test;


@Named
public class HelloTest {
    private static final Logger LOG = Logger.getLogger(HelloTest.class);

    @Inject
    SpringBean springBean;

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test.");

        // LOG.info("springBean.getMessage() = " + springBean.getMessage());

        Properties properties = System.getProperties();

        LOG.info("properties: " + properties);

        LOG.info("user.name: " + properties.getProperty("user.name"));

        double a = 3 + 2 / 5 + 2;

        LOG.info(a);

        int ints[] = { 1, 2, 3 };

        LOG.debug("ints[] = " + ints);
        LOG.debug("List = " + Arrays.asList(ints));

        Integer ios[] = new Integer[ints.length];

        for (int i = 0; i < ints.length; i++) {
            ios[i] = ints[i];
        }

        LOG.debug("ios[] = " + ios);
        LOG.debug("List = " + Arrays.asList(ios));

        Map<String, String> map = new HashMap<>();
        map.put("1", "one");
        map.put("3", "three");
        map.put("2", "two");

        LOG.debug(map);

        LOG.debug("Callstack" + Thread.currentThread().getStackTrace().toString());

        String str = "";
        int index = "ABCDE".indexOf(str);
        LOG.debug("index = " + index);

        LOG.info("End Test.");

    }
}
