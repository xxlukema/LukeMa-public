package com.learn.eventbus.listener;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;


public class NumberListener {
    private Number lastMessage;

    private static final Logger LOG = LogManager.getLogger(NumberListener.class);

    @Inject
    public NumberListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    public void listen(Number integer) {

        LOG.info("NumberListener received Number: " + integer);

        lastMessage = integer;
    }

    public Number getLastMessage() {
        return lastMessage;
    }
}
