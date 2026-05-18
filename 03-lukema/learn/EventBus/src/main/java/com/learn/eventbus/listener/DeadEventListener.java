package com.learn.eventbus.listener;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.DeadEvent;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.name.Named;


public class DeadEventListener {

    private static final Logger LOG = LogManager.getLogger(DeadEventListener.class);

    boolean notDelivered = false;

    @Inject
    public DeadEventListener(@Named("withId") EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    public void listen(DeadEvent event) {
        LOG.info("DeadEent: source = " + event.getSource());
        LOG.info("DeadEent: event = " + event.getEvent());

        notDelivered = true;
    }

    public boolean isNotDelivered() {
        return notDelivered;
    }
}
