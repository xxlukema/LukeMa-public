package com.learn.test.other;



import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class OtctalTest {

    @Test
    public void testOctal() {

        /**
         * 010 == 01_0 == 0_1_0
         */

        int i = 0_1_0;

        log.debug("010: {}", () -> i);

        /**
         * 080 090 are out of range compile time error.
         */
        int n = 070;

        log.debug("010: {}", () -> n);

        /**
         * (8 ^ 1 + 8 ^ 0) | 4
         */
        log.debug("010 | 4: {}", () -> 010 | 4);
        log.debug("10 | 4: {}", () -> 10 | 4);

    }
}
