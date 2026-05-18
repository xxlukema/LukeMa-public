package com.learn.json;


import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.learn.shein.mongo.model.SheinItem;
import com.learn.util.JsonUtils;

import lombok.extern.log4j.Log4j2;


@Log4j2
class JsonObjectTest {

    // String fileName = "C:\\D\\03-lukema\\LukeMa\\03-lukema\\learn\\HelloSpringBootService3\\test-data\\shein-item.json";
    // String fileName = "C:/D/03-lukema/LukeMa/03-lukema/learn/HelloSpringBootService3/test-data/shein-item.json";
    String fileName = "test-data/shein-item.json";

    @Test
    void testGreeting()
        throws IOException {

        log.debug(() -> "Begin test.");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(fileName).getFile());
        String json = FileUtils.readFileToString(file, StandardCharsets.UTF_8);

        log.debug("json: {}", () -> json);

        try {
            SheinItem item = JsonUtils.toObject(json, SheinItem.class);
            log.debug("item: {}", () -> item);
        } catch (JsonProcessingException e) {
            log.error("Exception: {}", e.getMessage());
        }

        log.debug(() -> "End test.");
    }
}
