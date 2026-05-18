package com.learn.bbb;


import javax.inject.Named;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


@Named
public class EnumTest {
    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        LOG.info("Value of Yes: " + Enum1.Yes);
        LOG.info("valueOf Yes: " + Enum1.valueOf("Yes"));

        LOG.info("Enum1.Yes.name(): " + Enum1.Yes.name());
        
        // Exception
        // LOG.info("valueOf Yes: " + Enum1.valueOf("Yes2"));

        LOG.info("Value of A: " + Enum2.A);
        LOG.info("valueOf a: " + Enum2.valueOf("A"));

        // Exception
        // LOG.info("valueOf a: " + Enum2.valueOf("a"));
        
        LOG.info("Enum2.class.getCanonicalName(): " + Enum2.class.getCanonicalName());

        LOG.info("End Test.");

    }
}


enum Enum1 {
    Yes, No;
}


enum Enum2 {
    A("a"), B("b"), C("c");

    private String value;

    Enum2(String value) {
        this.setValue(value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
