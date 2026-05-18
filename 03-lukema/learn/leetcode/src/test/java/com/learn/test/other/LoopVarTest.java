package com.learn.test.other;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class LoopVarTest {

    int N = 1_000_000;

    @Test
    public void testLoopVar1() {

        long start2 = System.nanoTime();
        for (int i = 1; i < N; i++) {
        }

        log.debug("Time: {}", () -> String.format("%,d", System.nanoTime() - start2));
    }

    @Test
    public void testLoopVar2() {

        long start = System.nanoTime();
        for (int i = 0; i < N - 1; i++) {
        }

        log.debug("Time: {}", () -> String.format("%,d", System.nanoTime() - start));

    }
}
