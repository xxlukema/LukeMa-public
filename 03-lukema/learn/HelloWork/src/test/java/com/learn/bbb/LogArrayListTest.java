package com.learn.bbb;


import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class LogArrayListTest {
    protected static final Logger LOG = LogManager.getLogger();

    @Test
    public void testArrayToList()
        throws Exception {
        String[] array = { "Line one", "Line 2", "Line three", };

        LOG.debug("Array: " + array);

        List<String> list = Arrays.asList(array);

        LOG.debug("List: " + list);
    }
}
