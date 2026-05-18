package com.broadsoft.cpbx.e911.provider;

import javax.inject.Singleton;

import org.glassfish.hk2.api.Factory;

import com.google.common.eventbus.EventBus;

/**
 * Simple singleton that allows us to register a global event
 * bus. This was handled via guice in the past but I removed
 * the guice dependency for simplicity.
 * @author chris
 *
 */
@Singleton
public class GlobalEventBusFactory implements Factory<EventBus> {
   
	private static final EventBus eventBus = new EventBus();
	
	@Override
	public EventBus provide() {
		return eventBus;
	}

	@Override
	public void dispose(EventBus instance) {

	}
	
}
