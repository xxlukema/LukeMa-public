package com.learn.test;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class ExceptionTest {

    @Test()
    public void testException() {

        Assertions.assertThrows(Exception.class, () -> {
            doThrowException();
        });

        log.debug("Test complete.");

    }

    void doThrowException()
        throws Exception {
        throw new Exception("Here You Go");
    }
}
