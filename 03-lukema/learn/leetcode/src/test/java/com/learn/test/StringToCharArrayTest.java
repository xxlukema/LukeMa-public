package com.learn.test;


import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class StringToCharArrayTest {

    @Test
    public void testToCharArray() {

        String str = "abcd";
        char[] chs = str.toCharArray();
        chs[0] = 'M';

        log.debug("str: {}", str);
        log.debug("chs: {}", chs);
    }
}
