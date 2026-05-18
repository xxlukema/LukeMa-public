package com.learn.json2xsd;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class JsonSchemaValidatorTest {

    @Test
    public void testProductValidator()
        throws Exception {
        log.info(() -> "Begin Test.");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);

        try (InputStream jsonStream = inputStreamFromClasspath("json2xsd/product.json");
                InputStream schemaStream = inputStreamFromClasspath("json2xsd/product-schema.json")) {

            JsonNode json = objectMapper.readTree(jsonStream);
            JsonSchema schema = schemaFactory.getSchema(schemaStream);
            Set<ValidationMessage> validationResult = schema.validate(json);

            // print validation errors
            if (validationResult.isEmpty()) {
                log.info(() -> "no validation errors :-)");
            } else {
                validationResult.forEach(vm -> System.out.println(vm.getMessage()));
            }
        }

        log.info(() -> "End Test.");

    }

    private InputStream inputStreamFromClasspath(String path)
        throws URISyntaxException, FileNotFoundException {

        /**
         * This does not work. Exception in thread "main" java.lang.IllegalArgumentException: argument "in" is null
         * return JsonSchemaValidator.class.getResourceAsStream(url.getPath());
         */
        // return JsonSchemaValidator.class.getResourceAsStream(url.getPath());

        URL url = Thread.currentThread().getContextClassLoader().getResource(path);

        log.info("Path: " + url.getPath());

        File file = new File(url.toURI());
        if (file.exists()) {
            log.debug(() -> "File exists");
        } else {
            log.debug(() -> "File NOT exists");
        }

        InputStream inputStream = new FileInputStream(file);

        return inputStream;
    }

}
