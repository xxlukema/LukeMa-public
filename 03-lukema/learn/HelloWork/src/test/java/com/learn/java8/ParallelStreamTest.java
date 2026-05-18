package com.learn.java8;


import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class ParallelStreamTest {

    @Test
    public void runTest()
        throws Exception {
        log.info(() -> "Begin Test");

        Path path = Paths.get(this.getClass().getClassLoader().getResource("com").toURI());

        try (Stream<String> lines = Files.lines(path, StandardCharsets.ISO_8859_1).parallel()) {

            // @formatter:off
            lines
                 .map(String::trim)
                 .map(str->str.toUpperCase())
                 .filter(str -> str != null && str.length() > 0)
                 .filter(str -> str.contains(" "))
                 .forEach(System.out::println);
            // @formatter:on

        }

        log.info(() -> "End Test.");

    }

}
