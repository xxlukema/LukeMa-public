package com.learn.bbb;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class StringPatternMatchTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

// @formatter:off
        String input = 
"This is my input string 1. This is my input string 2.";
// @formatter:on

        // Pattern pattern = Pattern.compile("is.*is");  // greedy
        Pattern pattern = Pattern.compile("is.*?is"); // non-greedy

        Matcher matcher = pattern.matcher(input);

        LOG.info(matcher);

        LOG.info(matcher.matches());

        while (matcher.find()) {
            LOG.info("---: " + matcher.groupCount());
            LOG.info(matcher.start() + " " + matcher.end());
            LOG.info(matcher.group());
        }

        LOG.info("End Test.");

    }
}
