package com.learn.java8.test;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class StringSplitJoinWithNullTest {

    Character delim = Character.valueOf((char) 256);

    @Test
    public void testJoin() {
        // List<String> list = List.of("Hello", null, "World");

        List<String> list = new ArrayList<>();

        list.add("Hello");
        list.add(null);
        list.add("");
        list.add("World");

        String str = list.stream().collect(Collectors.joining(String.valueOf(delim)));

        log.debug("string: {}", () -> str);

        String[] fields = str.split(String.valueOf(delim));

        log.debug("fields: {}", () -> fields);

        for (String f : fields) {
            if (f == null) {
                log.debug(() -> "f is null");
            } else if (f.isEmpty()) {
                log.debug(() -> "f is empty");
            } else {
                if (f.equals("null")) {
                    log.debug(() -> "f is String \"null\"");
                } else {
                    log.debug(() -> f);
                }
            }
        }

    }
}
