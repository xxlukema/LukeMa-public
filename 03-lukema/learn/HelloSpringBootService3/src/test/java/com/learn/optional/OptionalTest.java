package com.learn.optional;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
class OptionalTest {

    @Test
    void testEmpty() {
        OptionalInteger optionalInteger = new OptionalInteger();
        Integer emptyValue = optionalInteger.getEmptyValue();
        log.info("Empty: {}", emptyValue);
        assertNull(emptyValue);
    }

    @Test
    void testOfValue() {
        OptionalInteger optionalInteger = new OptionalInteger();
        Integer value = optionalInteger.getOfValue();
        log.info("Value: {}", value);
        assertEquals(1, value);
    }

}
