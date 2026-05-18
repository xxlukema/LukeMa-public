package com.learn.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSResourceResolver;


public class MyLSResourceResolver
    implements LSResourceResolver {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public LSInput resolveResource(String type, String namespaceURI, String publicId, String systemId, String baseURI) {

        LOG.debug(publicId);
        LOG.debug(systemId);
        LOG.debug(baseURI);
        LOG.debug(namespaceURI);
        LOG.debug(type);

        return null;
    }

}
