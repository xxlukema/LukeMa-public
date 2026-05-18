package com.learn.bbb;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class ListTest {
    private static final Logger LOG = LogManager.getLogger();

    //private static final String QuantityFormat = "######0.00";

    @Test
    public void testMain()
        throws Exception {
        LOG.info("Begin Test.");

        List<Integer> list = new ArrayList<>();
        list.add(1);

        LOG.info(list.get(0));

        LOG.info("End Test.");

    }
}
