package com.learn.aaa;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class AnotherNewTest {

    @Test
    public void testRun() {

        log.debug("Start: {}", () -> "test string");

        List<String> list = new ArrayList<>();

        List<List<String>> ll = new ArrayList<>();

        log.debug("class name: {}", () -> list.getClass().getCanonicalName());
        log.debug("class name: {}", () -> ll.getClass().getTypeParameters()[0]);

    }

}
