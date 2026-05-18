package com.learn.java14;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class TestRecord {

    @Test
    public void testRecord() {

        record Point(int x, int y) {
        }

        log.debug("Record: {}", () -> new Point(1, 2));

    }
}
