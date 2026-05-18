package com.learn.bbb;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class AaTest {

    @Test
    public void testMain()
        throws Exception {

        log.info("Begin Test.", () -> "");

        log.info(() -> "End Test.");

    }
}
