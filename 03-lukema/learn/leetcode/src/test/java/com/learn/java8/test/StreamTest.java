package com.learn.java8.test;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class StreamTest {

    /**
     * filtering 1 // 1 doesn't pass the filter
     * filtering 2 // 2 passes the filter, moves on to map
     * mapping 2 // 2 passes the map and limit steps and is added to output list
     * filtering 3 // 3 doesn't pass the filter
     * filtering 4 // 4 passes the filter, moves on to map
     * mapping 4 // 4 passes the map and limit steps and is added to output list
     */
    @Test
    public void testStreamExecuteOrder() {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);

        List<Integer> twoEvenSquares = numbers.stream().filter(n -> {
            log.debug("filtering {}", n);
            return n % 2 == 0;
        }).map(n -> {
            log.debug("mapping {}", n);
            return n * n;
        }).limit(2).collect(Collectors.toList());

        log.debug("twoEvenSquares: {}", twoEvenSquares);
    }

    @Test
    public void testFileStream() {

        String fileName = "log4j2.xml";

        Path path;
        try {
            path = Paths.get(ClassLoader.getSystemResource(fileName).toURI());
        } catch (URISyntaxException e) {
            log.error("URISyntaxException: {}", e.getMessage(), e);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileName)))) {
            Stream<String> stream = reader.lines();
            log.debug(() -> "here");
            stream.close();
        } catch (Exception e) {
            log.error("IOException: {}", e.getMessage(), e);
        }

        try (Stream<String> stream = Files.lines(path)) {
            stream.limit(3).forEach(log::debug);
            // stream.limit(10).findFirst().ifPresent(log::debug);
            log.debug(() -> "here");
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage(), e);
        }

        try (Stream<String> stream = Files.lines(path, Charset.forName("UTF-8"))) {
            stream.findFirst().ifPresent(log::debug);
            log.debug(() -> "here");
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage(), e);
        }
    }

    @Test
    public void testEmptyStream() {
        var stream = streamOf(null);
        stream.forEach(log::debug);
    }

    /**
     * Use the empty() method upon creation to avoid returning null for streams with no element
     */
    public Stream<String> streamOf(List<String> list) {
        return list == null || list.isEmpty() ? Stream.empty() : list.stream();
    }
}
