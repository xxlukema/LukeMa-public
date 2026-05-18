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
import com.google.inject.name.Named;
import com.google.inject.name.Names;
import com.learn.eventbus.listener.DeadEventListener;
import com.learn.eventbus.listener.IntegerListener;
import com.learn.eventbus.listener.MultipleListener;
import com.learn.eventbus.provider.EventBusProvider;


public class MultipleListenerTest {

    private static final Logger LOG = LogManager.getLogger(EventListenerTest.class);

    @Inject
    private EventBus eventBus1;

    @Inject
    private EventBus eventBus2;

    @Inject
    @Named("withId")
    private EventBus eventBusWithId;

    @Inject
    private MultipleListener multipleListener;

    @Test
    public void testSingleton()
        throws Exception {

        LOG.info("Begin Test.");

        Module module = new Module() {
            @Override
            public void configure(Binder binder) {
                binder.bind(EventBus.class).in(Scopes.SINGLETON);
                binder.bind(EventBus.class).annotatedWith(Names.named("withId")).toProvider(EventBusProvider.class).in(Scopes.SINGLETON);
                binder.bind(IntegerListener.class).in(Scopes.SINGLETON);
                binder.bind(DeadEventListener.class).in(Scopes.SINGLETON);
            }
        };

        Injector injector = Guice.createInjector(Stage.PRODUCTION, module);
        injector.injectMembers(this);

        LOG.info("EventBus 1: " + eventBus1.toString());
        LOG.info("EventBus 2: " + eventBus2.toString());

        LOG.info("EventBus with Id: " + eventBusWithId.toString());

        // when
        eventBusWithId.post(new Integer(100));
        eventBusWithId.post(new Long(800));
        eventBusWithId.post("Hello EventBus!");

        // then
        Assert.assertTrue(multipleListener.getLastInteger() == 100);
        Assert.assertTrue(multipleListener.getLastLong() == 800L);

        LOG.info("End Test.");
    }
}
