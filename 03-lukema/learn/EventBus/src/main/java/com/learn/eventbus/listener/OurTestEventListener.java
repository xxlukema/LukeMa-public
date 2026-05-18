package com.learn.eventbus.listener;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.learn.eventbus.event.OurTestEvent;
import com.learn.eventbus.test.EventListenerTest;


public class OurTestEventListener {

    private static final Logger LOG = LogManager.getLogger(EventListenerTest.class);

    public int lastMessage = 0;

    @Inject
    public OurTestEventListener(EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    public void listen(OurTestEvent event) {

        LOG.info("OurTestEventListener received OurTestEvent: " + event.getMessage());

        lastMessage = event.getMessage();
    }

    public int getLastMessage() {
        return lastMessage;
    }
}
