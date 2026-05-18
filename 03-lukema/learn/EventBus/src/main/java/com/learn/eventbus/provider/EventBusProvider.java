package com.learn.eventbus.provider;


import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.inject.Provider;


public class EventBusProvider
    implements Provider<EventBus> {

    private static final Logger LOG = LogManager.getLogger(EventBusProvider.class);

    private static final AtomicInteger Counter = new AtomicInteger(0);

    private int id = 0;

    public EventBusProvider() {
        id = Counter.incrementAndGet();

        LOG.info("EventBusProvider constructor: id = " + id);
    }

    @Override
    public EventBus get() {
        return EventBusFactory.newInstance();
    }

}
