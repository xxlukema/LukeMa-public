package com.learn.bbb;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SlashyStringTest {

    @Test
    public void testMain()
        throws Exception {

        log.info("Begin Test.", () -> "");

        // String str = /Hello Wold!/;
        // log.info("str: {}", ()->str);

        log.info(() -> "End Test.");

    }
}
