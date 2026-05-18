package com.learn.util;


import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class NcmValidationEventHandler
    implements ValidationEventHandler {

    private static final Logger log = LogManager.getLogger();

    public boolean handleEvent(ValidationEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append("EVENT");
        sb.append("\nSEVERITY:  ").append(event.getSeverity());
        sb.append("\nMESSAGE:  ").append(event.getMessage());
        sb.append("\nLINKED EXCEPTION:  ").append(event.getLinkedException());
        sb.append("\nLOCATOR");
        sb.append("\n    LINE NUMBER:  ").append(event.getLocator().getLineNumber());
        sb.append("\n    COLUMN NUMBER:  ").append(event.getLocator().getColumnNumber());

        log.info(sb);

        return true;
    }

}
