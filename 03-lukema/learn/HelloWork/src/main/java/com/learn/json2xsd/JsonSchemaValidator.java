package com.learn.json2xsd;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Set;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import lombok.extern.log4j.Log4j2;


/**
 * [Free Online JSON to JSON Schema Converter]<https://www.liquid-technologies.com/online-json-to-schema-converter>
 */
@Log4j2
public class JsonSchemaValidator {

    public static void main(String[] args) {
        JsonSchemaValidator jsonSchemaValidator = new JsonSchemaValidator();

        try {
            // jsonSchemaValidator.testProductValidator();

            // jsonSchemaValidator.testEventValidatorJson();

            jsonSchemaValidator.testEventValidatorXsd();
        } catch (Exception e) {
            log.error("Exception: {}", () -> e.getMessage(), () -> e);
        }
    }

    public void testEventValidatorXsd() {
        log.info(() -> "Begin Test.");

        validateXsdSchema("json2xsd/nms-to-cds/event-schema.xsd", "json2xsd/nms-to-cds/event.xml");

        log.info(() -> "End Test.");

    }

    public void testEventValidatorJson() {
        log.info(() -> "Begin Test.");

        validateJSONSchema("json2xsd/nms-to-cds/event-schema.json", "json2xsd/nms-to-cds/event.json");

        log.info(() -> "End Test.");

    }

    public void testProductValidator() {
        log.info(() -> "Begin Test.");

        validateJSONSchema("json2xsd/product-schema.json", "json2xsd/product.json");

        log.info(() -> "End Test.");

    }

    public boolean validateJSONSchema(String jsonSchemaPath, String jsonPath) {
        log.info(() -> "Begin validate JSON.");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V202012);

        try (InputStream jsonStream = inputStreamFromClasspath(jsonPath);
                InputStream schemaStream = inputStreamFromClasspath(jsonSchemaPath)) {

            JsonNode json = objectMapper.readTree(jsonStream);
            JsonSchema schema = schemaFactory.getSchema(schemaStream);

            Set<ValidationMessage> validationResult = schema.validate(json);

            // print validation errors
            if (validationResult.isEmpty()) {
                log.info(() -> "PASS: JSON no validation errors");
                return true;
            } else {
                validationResult.forEach(vm -> System.out.println(vm.getMessage()));
                log.error(() -> "FAIL: JSON Validation errors found.");
                return false;
            }
        } catch (IOException | URISyntaxException e) {
            log.error(() -> "FAIL: JSON Validation exception.");
            return false;
        } finally {
            log.info(() -> "End validate JSON.");
        }
    }

    private InputStream inputStreamFromClasspath(String path)
        throws URISyntaxException, FileNotFoundException {

        File file = fileFromClasspath(path);

        /*
        if (file.exists()) {
            log.debug(() -> "File exists");
        } else {
            log.debug(() -> "File NOT exists");
        }
        */

        InputStream inputStream = new FileInputStream(file);

        return inputStream;
    }

    private File fileFromClasspath(String path)
        throws URISyntaxException, FileNotFoundException {

        /**
         * This does not work. Exception in thread "main" java.lang.IllegalArgumentException: argument "in" is null
         * return JsonSchemaValidator.class.getResourceAsStream(url.getPath());
         */
        // return JsonSchemaValidator.class.getResourceAsStream(url.getPath());

        URL url = Thread.currentThread().getContextClassLoader().getResource(path);

        log.info("Path: " + url.getPath());

        File file = new File(url.toURI());

        return file;
    }

    public boolean validateXsdSchema(String xsdPath, String xmlPath) {

        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            // Schema schema = factory.newSchema(new File(xsdPath));
            File xsd = fileFromClasspath(xsdPath);
            Schema schema = factory.newSchema(xsd);

            Validator validator = schema.newValidator();
            File xml = fileFromClasspath(xmlPath);
            validator.validate(new StreamSource(xml));
        } catch (IOException | SAXException | URISyntaxException e) {
            log.error("Exception: {}", e.getMessage(), e);
            log.error(() -> "FAIL: XML Validation exception.");
            return false;
        }
        log.info(() -> "PASS: XML no validation errors");
        return true;
    }

}
