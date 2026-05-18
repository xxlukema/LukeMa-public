package com.learn.eventbus.listener;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.name.Named;


public class MultipleListener {

    private static final Logger LOG = LogManager.getLogger(MultipleListener.class);

    public Integer lastInteger;

    public Long lastLong;

    @Inject
    public MultipleListener(@Named("withId") EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    public void listenInteger(Integer event) {
        LOG.info("MultipleListener received Integer: " + event);

        lastInteger = event;
    }

    @Subscribe
    public void listenLong(Long event) {
        LOG.info("MultipleListener received Long: " + event);

        lastLong = event;
    }

    public Integer getLastInteger() {
        return lastInteger;
    }

    public Long getLastLong() {
        return lastLong;
    }
}
