package com.learn.eventbus.listener;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.google.inject.Inject;
import com.google.inject.name.Named;


public class IntegerListener {

    private static final Logger LOG = LogManager.getLogger(IntegerListener.class);

    @Inject
    public IntegerListener(@Named("withId") EventBus eventBus) {
        eventBus.register(this);
    }

    @Subscribe
    public void listen(Integer integer) {
        LOG.info("IntegerListener received: " + integer);
    }

}
