package com.broadsoft.cpbx.e911.servlet;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collection;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.atmosphere.cache.UUIDBroadcasterCache;
import org.atmosphere.config.service.AtmosphereHandlerService;
import org.atmosphere.cpr.AtmosphereResource;
import org.atmosphere.cpr.Broadcaster;
import org.atmosphere.cpr.BroadcasterFactory;
import org.atmosphere.handler.AbstractReflectorAtmosphereHandler;
import org.atmosphere.interceptor.AtmosphereResourceLifecycleInterceptor;
import org.atmosphere.interceptor.HeartbeatInterceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;


/**
 * This class is used to send comet notifications to the client's over
 * websockets or long polling. The interceptors configured are for managing
 * state and maintaining state. This implementation uses the hearbeat
 * interceptor to keep the client session alive. When a client connects via the 
 * /async/status?tn=123456789 we will grab the tn and use it to recognize the 
 * broadcaster. 
 * 
 * @author chris
 */
@AtmosphereHandlerService(path = "/async/status", broadcasterCache = UUIDBroadcasterCache.class, interceptors = { AtmosphereResourceLifecycleInterceptor.class,
        HeartbeatInterceptor.class })
public class CometStatusHandler
    extends AbstractReflectorAtmosphereHandler {

    /**
     * BTBC Status Event Filter. These are the valid events that will be sent to the
     * BTBC client.
     */
    private static final int[] BTBC_VALID_EVENTS = new int[] { StatusEvent.SUCCESS, StatusEvent.FAILURE, StatusEvent.PENDING, StatusEvent.POLLING_TIMEOUT,
            StatusEvent.USER_CANCEL, StatusEvent.TOKEN_EXPIRED };

    private static final Logger logger = LogManager.getLogger(CometStatusHandler.class);

    private final EventBus eventBus;

    /**
     * Default empty constructor.
     */
    @Inject
    public CometStatusHandler(final EventBus eventBus) {
        this.eventBus = eventBus;
        eventBus.register(CometStatusHandler.this);
    }

    /**
     * Register for an AddressPollingTimeout event. If this occurs we will fire
     * two events. We first SEND the POLLING_TIMEOUT event and then the failure
     * event. Hopefully the first event will be handled by the btbc client.
     * 
     * @param addressActiveEvent
     *            The status event that will be sent to client.
     */
    @Subscribe
    public void onAddressPollingTimeout(AddressPollingTimeoutEvent addressPollingTimeoutEvent) {

        final String tn = addressPollingTimeoutEvent.getTn();
        if (tn == null) {
            throw new IllegalArgumentException("Tn cannot be null");
        }

        fireStatusEvent(tn, StatusEvent.create(tn, StatusEvent.POLLING_TIMEOUT, "TIMEOUT EXCEEDED"));
        fireStatusEvent(tn, StatusEvent.create(tn, StatusEvent.FAILURE, "TIMEOUT EXCEEDED"));
    }

    /**
     * Uses eventbus to register for onStatusEvents to send to the client. The
     * {@link StatusEvent} will be converted to json and send to the client
     * via a push.
     * 
     * @param addressActiveEvent
     *            The status event that will be sent to client.
     */
    @Subscribe
    public void onAddressPendingEvent(AddressPendingEvent addressPendingEvent) {

        final String tn = addressPendingEvent.getTn();
        if (tn == null) {
            throw new IllegalArgumentException("Tn cannot be null");
        }

        fireStatusEvent(tn, StatusEvent.create(tn, StatusEvent.PENDING, "PENDING ADDRESS CHANGE FOR TN " + addressPendingEvent.getTn()));
    }

    /**
     * Uses eventbus to register for onStatusEvents to send to the client. The
     * {@link StatusEvent} will be converted to json and send to the client
     * via a push.
     * 
     * @param addressActiveEvent
     *            The status event that will be sent to client.
     */
    @Subscribe
    public void onAddressActiveEvent(AddressOrderCompletionEvent addressActiveEvent) {

        final String tn = addressActiveEvent.getTn();
        if (tn == null) {
            throw new IllegalArgumentException("Tn cannot be null");
        }
        fireStatusEvent(tn, StatusEvent.create(tn, StatusEvent.SUCCESS, "ACTIVE"));
    }

    private void fireStatusEvent(final String tn, StatusEvent statusEvent) {
        debug();

        Broadcaster broadcaster = lookupBroadcaster(tn, false);
        if (broadcaster != null) {
            broadcaster.broadcast(statusEvent);
            logger.info("Sent event to broacaster with tn " + tn);
        } else {
            logger.info("Broadcaster was not found for tn " + tn);
        }
    }

    /**
     * Used to look up a broadcaster by it's internal tn.
     * 
     * @param tn
     *            The telephone number for the session
     * @return The broadcaster or null if not found.
     */
    private Broadcaster lookupBroadcaster(String tn, boolean createIfNull) {
        if (tn == null) {
            return null;
        }

        Broadcaster broadcaster = BroadcasterFactory.getDefault().lookup("/" + tn, createIfNull);
        return broadcaster;
    }

    /**
     * When a request is retrieved we will handle suspending the request.
     * 
     * @param resource
     * @throws IOException
     */
    @Override
    public void onRequest(AtmosphereResource resource)
        throws IOException {
        final String method = resource.getRequest().getMethod();
        logger.debug("OnRequest: method" + method);
        if (method.equals("GET")) {
            doGet(resource);
        } else if (method.equals("POST")) {
            doPost(resource);
        }
    }

    /**
     * The get method will handle an http get method which is what the btbc client uses to 
     * fetch status events. The first thing we do is suspend the connection. This is important
     * because otherwise the client will repeatedly reconnect as fast as it can. We check
     * for the tn parameter as part of the url ?tn=23434234. If this is available we use
     * it to create a broadcaster for the btbc client and assign the tn as the key for lookup.
     * 
     * 
     * @param resource
     * @throws UnsupportedEncodingException
     */
    public void doGet(AtmosphereResource resource)
        throws UnsupportedEncodingException {
        resource.suspend();
        resource.resumeOnBroadcast(true);

        // Remove all filters so that we don't have one left around.
        resource.getBroadcaster().getBroadcasterConfig().removeAllFilters();

        String tn = resource.getRequest().getParameter("tn");
        logger.debug("Received event registration request for tn" + tn);

        if (tn != null) {
            logger.debug("Adding broadcaster for tn " + tn + " with broadcaster id " + resource.getBroadcaster().getID());
            Broadcaster singleBroadcaster = lookupBroadcaster(tn, true);
            singleBroadcaster.addAtmosphereResource(resource);
            resource.setBroadcaster(singleBroadcaster);

        }

        // If the eventFilter is not null then we will configure the filter
        // accordingly.
        String eventFilter = resource.getRequest().getParameter("eventFilter");
        StatusEventFilter statusEventFilter = new StatusEventFilter();
        if (eventFilter != null && eventFilter.equals("BTBC")) {
            statusEventFilter.setValidEvents(BTBC_VALID_EVENTS);
        } else {
            statusEventFilter.clearValidEvents();
        }

        resource.getBroadcaster().getBroadcasterConfig().addFilter(statusEventFilter);
    }

    /**
     * receive push message from client
     * 
     * @throws IOException
     **/
    public void doPost(AtmosphereResource resource)
        throws IOException {
        BufferedReader reader = resource.getRequest().getReader();

        String message = reader.readLine();

        if (message != null && !message.isEmpty()) {
            String tn = resource.getRequest().getParameter("tn");
            logger.debug("received message for " + tn);
            ObjectMapper objectMapper = new ObjectMapper();
            StatusEvent event = objectMapper.readValue(message, StatusEvent.class);
            lookupBroadcaster(tn, false).broadcast(event);
        }
        //debug();
    }

    /**
     * Not implemented
     */
    public void destroy() {
    }

    /**
     * Internal debug method to print out the listeners that are registered.
     * This method is protected so it can be called by the testing class.
     */
    protected void debug() {
        Collection<Broadcaster> broadcasters = BroadcasterFactory.getDefault().lookupAll();

        for (Broadcaster broadcaster : broadcasters) {
            logger.info("Broadcaster ID " + broadcaster.getID());
        }
    }

}
