package com.learn.util;


import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class MyErrorHandler
    implements ErrorHandler {

    @Override
    public void warning(SAXParseException e)
        throws SAXException {
        show("Warning", e);
        throw (e);
    }

    @Override
    public void error(SAXParseException e)
        throws SAXException {
        show("Error", e);
        throw (e);
    }

    @Override
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
        log.info(sb);
    }
}
