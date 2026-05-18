package com.learn.bbb;


import java.util.ArrayList;
import java.util.List;

import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


@Named
public class DiamondTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        List<String> list = new ArrayList<>();

        LOG.info(list.size());

        LOG.info("End Test.");

    }
}
