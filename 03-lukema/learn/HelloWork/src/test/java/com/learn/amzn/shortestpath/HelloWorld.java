package com.learn.amzn.shortestpath;


import java.util.Arrays;
import java.util.Comparator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class HelloWorld {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testSort() {
        LOG.info("Test begin.");

        String[] array = { "aa", "A", "Aa", "aA", "B", "11", "1" };

        LOG.info("111111111111111111111");
        Arrays.stream(array).sorted((o1, o2) -> o1.compareTo(o2)).forEach(LOG::info);
        LOG.info("222222222222222222222");
        Arrays.stream(array).sorted((o1, o2) -> o1.compareTo(o2) * (-1)).forEach(LOG::info);

        LOG.info("333333333333333333333");
        Comparator<String> comparator = (o1, o2) -> o1.compareTo(o2);
        Arrays.stream(array).sorted(comparator.reversed()).forEach(LOG::info);

        LOG.info("Test complete.");
    }
}
