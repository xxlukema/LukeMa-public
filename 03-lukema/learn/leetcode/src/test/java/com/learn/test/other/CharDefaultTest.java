package com.learn.test.other;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class CharDefaultTest {

    @Test
    public void testCharDefault() {

        log.debug(() -> "Start Test");

        char[] chs = new char[1];
        Assertions.assertTrue(chs[0] == Character.MIN_VALUE);

        log.debug(() -> "End Test");

    }
}
