package com.learn.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;


public class MyErrorHandler
    implements ErrorHandler {

    private static final Logger LOG = LogManager.getLogger();

    public void warning(SAXParseException e)
        throws SAXException {
        show("Warning", e);
        throw (e);
    }

    public void error(SAXParseException e)
        throws SAXException {
        show("Error", e);
        throw (e);
    }

    public void fatalError(SAXParseException e)
        throws SAXException {
        show("Fatal Error", e);
        throw (e);
    }

    private void show(String type, SAXParseException e) {
        StringBuilder sb = new StringBuilder();
        sb.append(type).append(": ").append(e.getMessage()).append("\n");
        sb.append("Line ").append(e.getLineNumber()).append(" Column ").append(e.getColumnNumber()).append("\n");
        sb.append("System ID: ").append(e.getSystemId());
        LOG.info(sb);
    }
}
