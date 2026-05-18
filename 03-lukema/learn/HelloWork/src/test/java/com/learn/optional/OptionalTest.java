package com.learn.optional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class OptionalTest {

    @Test
    public void testFindFirst()
        throws Exception {
        log.debug("Begin Test");

        List<String> list = List.of("One", "Two");
        // List<String> list = List.of();

        list.stream().findFirst().ifPresent(log::debug);

        Optional.ofNullable(list)
                .map(List::stream)
                .map(Stream::findFirst)
                .get()
                .ifPresent(v -> {
                    log.debug("== v: {}", v);
                });

        log.debug("End Test.");

    }

    @Test
    public void testOrElse()
        throws Exception {
        log.debug("Begin Test");

        // List<String> list = List.of("www");
        List<String> list = List.of();

        StringBuilder sb = new StringBuilder();

        Optional.ofNullable(list)
                .map(List::stream)
                .map(Stream::findFirst)
                .get()
                .ifPresentOrElse(
                        v -> {
                            log.debug("============== v: {}", v);
                            sb.append(v);
                        },
                        () -> {
                            log.debug("============== is null.");
                        });

        log.debug("=== sb: {}", sb.toString());

        log.debug("End Test.");

    }

}
