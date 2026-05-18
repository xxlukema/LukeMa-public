package com.learn.poi.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.learn.poi.config.BootAppConfig;


// @RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = { BootAppConfig.class })
public class PoiTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testFirst() {

        LOG.debug("Test starts.");

        LOG.debug("Test ends.");
    }

}
