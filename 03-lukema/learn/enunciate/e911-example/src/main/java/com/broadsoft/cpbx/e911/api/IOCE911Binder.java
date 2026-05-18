package com.broadsoft.cpbx.e911.api;

import javax.inject.Singleton;

import org.glassfish.hk2.utilities.binding.AbstractBinder;

import com.broadsoft.cpbx.e911.domain.AddressChangeSession;
import com.broadsoft.cpbx.e911.domain.WebSession;
import com.broadsoft.cpbx.e911.provider.AddressChangeDelegate;
import com.broadsoft.cpbx.e911.provider.GlobalEventBusFactory;
import com.broadsoft.cpbx.e911.provider.IAddressChangeAdapter;
import com.broadsoft.cpbx.e911.provider.IAddressValidationAdapter;
import com.broadsoft.cpbx.e911.provider.MockAddressChangeService;
import com.broadsoft.cpbx.e911.provider.MockAddressValidationAdapter;
import com.broadsoft.cpbx.e911.resource.AddressChangeResource;
import com.google.common.eventbus.EventBus;

public class IOCE911Binder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(AddressChangeResource.class).to(IAddressChangeServiceResource.class);
		bind(AddressChangeSession.class).to(AddressChangeSession.class);
		bind(WebSession.class).to(ISession.class);
		bindFactory(GlobalEventBusFactory.class).to(EventBus.class).in(Singleton.class);
		bind(AddressChangeDelegate.class).to(IAddressChangeService.class).in(Singleton.class);
		bind(MockAddressChangeService.class).to(IAddressChangeAdapter.class).in(Singleton.class);
		
		bind(MockAddressValidationAdapter.class).to(IAddressValidationAdapter.class).in(Singleton.class);

		
	}
}
