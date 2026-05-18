package com.learn.bbb;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class WrapperTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testMain()
        throws Exception {
        LOG.info("Begin Test.");

        String caller = Wrapper.create();

        LOG.info("caller = " + caller);
        
        String wrapper = Wrapper.getWrapper();

        LOG.info("wrapper = " + wrapper);

        LOG.info("End Test.");

    }
}
