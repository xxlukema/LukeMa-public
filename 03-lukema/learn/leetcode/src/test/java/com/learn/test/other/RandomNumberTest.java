package com.learn.test.other;


import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class RandomNumberTest {

    @Test
    public void testRandom() {
        int begin = 10, end = 99;

        int random = ThreadLocalRandom.current().nextInt(begin, end + 1);
        log.debug("ThreadLocalRandom: {}", random);

        random = new Random().nextInt(begin, end + 1);
        log.debug("Random: {}", random);

        random = (int) (Math.random() * (end - begin + 1)) + begin;
        log.debug("Math.random(): {}", random);
    }
}
