package com.learn.java8;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class LambdarExpressionTest {

    private static final Logger LOG = LogManager.getLogger();

    private int shadow = 1;

    @FunctionalInterface
    interface IntegerMath {
        int operation(int a, int b);
    }

    public int doIntegerMath(int a, int b, IntegerMath integerMath) {
        return integerMath.operation(a, b);
    }

    @Test
    public void testDoIntegerMath()
        throws Exception {
        LOG.info("Begin Test.");

        IntegerMath addition = (a, b) -> {
            return a + b + shadow;
        };
        IntegerMath subtraction = (a, b) -> a - b;
        LOG.info("40 + 2 = " + doIntegerMath(40, 2, addition));
        LOG.info("20 - 10 = " + doIntegerMath(20, 10, subtraction));

        LOG.info("End Test.");

    }
}
