package com.learn.java15;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class MultiLineString {

    @Test
    public void testMultiLine() {
        String str = """
                Multi line string 1
                line 2
                lin 3
                """;

        log.debug("str: {}", () -> str);

        var lines = str.lines().sorted().toList();

        log.debug("lines list: {}", () -> lines);

        var lines2 = str.lines().sorted().toArray();

        log.debug("lines array: {}", () -> lines2);
    }
}
