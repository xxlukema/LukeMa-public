package com.learn.json2xsd.resource;


import java.io.File;
import java.io.IOException;

import javax.xml.transform.Result;
import javax.xml.transform.stream.StreamResult;

import org.junit.jupiter.api.Test;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.SchemaOutputResolver;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class Josn2XsdResourceTest {

    @Test
    public void generateSchemas() {
        log.debug(() -> "Begin");
        generateXsd(ConfigFileStatus.class, "configFileStatus.xsd");
        log.debug(() -> "End");
    }

    public void generateXsd(Class<?> clazz, String fileName) {
        try {
            JAXBContext context = JAXBContext.newInstance(clazz);
            context.generateSchema(new SchemaOutputResolver() {
                @Override
                public Result createOutput(String namespaceURI, String suggestedFileName)
                    throws IOException {
                    File file = new File(fileName);
                    StreamResult result = new StreamResult(file);
                    result.setSystemId(file.toURI().toURL().toString());
                    return result;
                }
            });
        } catch (Exception e) {
            log.error("Exception generateXsd(): {}", () -> e.getMessage(), () -> e);
        }
    }

}
