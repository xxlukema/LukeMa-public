package com.learn.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;


public class MyLSResourceResolver
    implements LSResourceResolver {

    private static final Logger log = LogManager.getLogger();

    @Override
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {

        log.debug(publicId);
        log.debug(systemId);
        log.debug(baseURI);
        log.debug(namespaceURI);
        log.debug(type);

        return null;
    }

}
