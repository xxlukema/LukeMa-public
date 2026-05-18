package com.learn.java10;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class VarTest {
    // private static final Logger log = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        log.info("Begin Test");

        var str = "Hello World!";

        log.info("str: {}", str);

        int i = 010;
        int k = 07;

        log.info("End Test: {}, {}", i, k);

        str = String.format("%.06f", (float) 2 / 3);

        log.debug("str: {}", str);

    }
}
