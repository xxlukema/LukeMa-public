package com.learn.pnc;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class HelloTest {

    @Test
    public void runTest()
        throws Exception {
        log.info("Begin Test");

        String fileName = "initName.txt";

        log.info(fileName);

        log.info("End Test.");

    }
}
