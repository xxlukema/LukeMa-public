package com.learn.test.other;


import java.util.Random;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class RandomTest {

    @Test
    public void testRandom() {

        log.debug("randowm 1: {}", () -> Math.random());
        log.debug("randowm 1: {}", () -> new Random(System.currentTimeMillis()).nextInt(100));

    }
}
