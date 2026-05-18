package com.learn.collab;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class FabNumberGenerator {

    private static final Logger LOG = LogManager.getLogger();

    public void startGen() {

        int seed = 0;
        int next = 1;

        int generated = genNext(seed, next);

        LOG.info("generated = " + generated);

        for (int i = 0; i < 10; i++) {
            seed = next;
            next = generated;
            generated = genNext(seed, next);
            LOG.info("generated = " + generated);
        }

    }

    public int genNext(int a, int b) {
        return a + b;
    }

    public int genNextException(int a, int b)
        throws Exception {

        if (a > 100 || b > 100) {
            throw new Exception("Input is too big. Should be less than 100.");
        }

        return a + b;
    }

}
