package com.learn.eventbus.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.eventbus.EventBus;
import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.Stage;
import com.learn.eventbus.event.OurTestEvent;
import com.learn.eventbus.listener.OurTestEventListener;


public class EventListenerTest {

    private static final Logger LOG = LogManager.getLogger(EventListenerTest.class);

    @Inject
    private EventBus eventBus;

    @Inject
    private OurTestEventListener ourTestEventListener;

    @Test
    public void testSingleton()
        throws Exception {

        LOG.info("Begin Test.");

        Module module = new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(EventBus.class).in(Scopes.SINGLETON);
            }
        };

        Injector injector = Guice.createInjector(Stage.PRODUCTION, module);
        injector.injectMembers(this);

        // when
        eventBus.post(new OurTestEvent(200));

        // then
        Assert.assertTrue(ourTestEventListener.getLastMessage() == 200);

        LOG.info("End Test.");
    }
}
