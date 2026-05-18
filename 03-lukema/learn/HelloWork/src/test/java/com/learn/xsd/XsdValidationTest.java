package com.learn.xsd;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.learn.util.ClasspathUtils;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class XsdValidationTest {

    @Test
    public void testValidatorPass()
        throws URISyntaxException, IOException, SAXException {
        log.debug(() -> "Start");

        File xmlFile = new File(ClasspathUtils.getURI("xsd/Person.xml"));
        File xsdFile = new File(ClasspathUtils.getURI("xsd/Person.xsd"));

        Validator validator = initValidator(xsdFile);

        XmlErrorHandler xmlErrorHandler = new XmlErrorHandler();
        validator.setErrorHandler(xmlErrorHandler);

        validator.validate(new StreamSource(xmlFile));

        log.debug(() -> "End");
    }

    @Test
    public void testValidatorFail()
        throws URISyntaxException, IOException, SAXException {
        log.debug(() -> "Start");

        PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = patternResolver.getResources("classpath:xsd/*-full.xsd");

        Source[] sources = new Source[resources.length];
        for (int i = 0; i < resources.length; i++) {
            sources[i] = new StreamSource(resources[i].getInputStream());
        }

        File xmlFile = new File(ClasspathUtils.getURI("xsd/Person.xml"));
        // File xsdFile = new File(ClasspathUtils.getURI("xsd/Person-full.xsd"));

        Validator validator = initValidator(sources);

        XmlErrorHandler xmlErrorHandler = new XmlErrorHandler();
        validator.setErrorHandler(xmlErrorHandler);

        try {
            validator.validate(new StreamSource(xmlFile));
        } catch (SAXParseException e) {
            log.error("Validation Error {}", e.getMessage());
        }

        xmlErrorHandler.getExceptions().forEach(e -> {
            log.debug("Line: {}, Column: {}, meg: {}", e.getLineNumber(), e.getColumnNumber(), e.getMessage());
        });

        log.debug(() -> "End");
    }

    private Validator initValidator(File xsdFile)
        throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(xsdFile);
        Schema schema = factory.newSchema(schemaFile);
        return schema.newValidator();
    }

    private Validator initValidator(Source[] sources)
        throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = factory.newSchema(sources);
        return schema.newValidator();
    }
}
