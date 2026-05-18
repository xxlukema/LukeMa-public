package com.broadsoft.cpbx.e911.servlet;


import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.atmosphere.cpr.BroadcastFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.primitives.Ints;


/**
 * This is an event filter that will transform a status event to json. It also handles
 * managing a range of valid events. If the validEvents are specified then the 
 * @author chris
 *
 */
public class StatusEventFilter
    implements BroadcastFilter {

    private static final Logger logger = LogManager.getLogger(StatusEventFilter.class.getName());

    /**
     * List of valid events for this filter, if null then all events
     * will pass through the filter and be converted to json.
     */
    private int[] validEvents = null;

    /**
     * Object mapper is the json mapper for converting status events
     * to json text before sending to client.
     */
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Default constructor will initialize the validEvents to null which will
     * allow all events to pass through the filter.
     */
    public StatusEventFilter() {
        this(null);
    }

    public StatusEventFilter(int[] validEvents) {
        setValidEvents(validEvents);
    }

    public void setValidEvents(int[] validEvents) {
        this.validEvents = validEvents;
    }

    public void clearValidEvents() {
        this.validEvents = null;
    }

    @Override
    public BroadcastAction filter(Object originalMessage, Object message) {
        if (!(originalMessage instanceof StatusEvent)) {
            logger.debug("Ignoring event because it is not a status Event");
            return new BroadcastAction(BroadcastAction.ACTION.ABORT, message);
        }

        StatusEvent statusEvent = (StatusEvent) originalMessage;
        if (validEvents != null && !Ints.contains(validEvents, statusEvent.getStatusCode())) {
            return new BroadcastAction(BroadcastAction.ACTION.ABORT, message);
        }

        return new BroadcastAction(BroadcastAction.ACTION.CONTINUE, eventToJsonString(statusEvent));
    }

    /**
     * Helper to convert the status event to a json string.
     * @param event
     * @return
     */
    private String eventToJsonString(StatusEvent event) {
        String value = null;
        try {
            value = objectMapper.writeValueAsString(event);
        } catch (IOException ex) {
            logger.error("Exception", ex);
        }
        return value;
    }
}
