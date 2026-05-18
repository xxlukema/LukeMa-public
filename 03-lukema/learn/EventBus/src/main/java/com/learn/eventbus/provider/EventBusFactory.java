package com.learn.eventbus.provider;


import java.util.concurrent.atomic.AtomicInteger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;


public class EventBusFactory {

    private static final Logger LOG = LogManager.getLogger(EventBusFactory.class);

    private static final AtomicInteger Counter = new AtomicInteger(0);

    private int id = 0;

    private static final EventBus TheEventBus = new EventBus("MyEventBusId");

    public EventBusFactory() {
        id = Counter.incrementAndGet();

        LOG.info("EventBusFactory constructor: id = " + id);
    }

    public static EventBus newInstance() {
        return TheEventBus;
    }

}
