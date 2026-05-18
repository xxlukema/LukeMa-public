package com.learn.java11;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class StringAndFile {

    @Test
    public void testStringAndFile() {

        "Marco".isBlank();
        "Mar\nco".lines();
        "Marco  ".strip();

        Path path = null;
        try {
            path = Files.writeString(Files.createTempFile("helloworld", ".txt"), "Hi, This is file \"temp file\" test!");
        } catch (IOException e) {
            log.error("IOException: {}", e.getMessage(), e);
        }

        if (path != null) {
            try {
                String s = Files.readString(path);
                log.debug("s: {}", () -> s);
            } catch (IOException e) {
                log.error("IOException: {}", e.getMessage(), e);
            }

        }

    }

}
