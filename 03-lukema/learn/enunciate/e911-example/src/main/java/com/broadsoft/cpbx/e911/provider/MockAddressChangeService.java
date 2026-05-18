package com.broadsoft.cpbx.e911.provider;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.jvnet.hk2.annotations.Service;

import com.broadsoft.cpbx.e911.api.AddressValidationException;
import com.broadsoft.cpbx.e911.api.VendorException;
import com.broadsoft.cpbx.e911.domain.Address;
import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;

@Service
public class MockAddressChangeService implements IAddressChangeAdapter, IAddressValidationAdapter {

	private static final Map<String, Address> mockAddressCache = createAddressCache();

	private final EventBus eventBus;

	@Inject
	public MockAddressChangeService(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void cancelAddressChange(String tn) throws VendorException {

	};

	@Override
	public void resetAddress(String tn) throws VendorException {

	}

	@Override
	public Address validateAddress(Address address) throws AddressValidationException {
		return address;
	}

	@Override
	public List<Address> getPreviousAddresses(String tn, int count) throws VendorException {
		return Lists.newArrayList(mockAddressCache.get(tn));
	}

	@Override
	public Address getCurrentAddress(String tn) throws VendorException {
		return mockAddressCache.get(tn);
	}

	@Override
	public Address getPrimaryAddress(String tn) throws VendorException {
		return mockAddressCache.get(tn);
	}

	@Override
	public List<Address> getPreviousAddresses(String tn) throws VendorException {
		return Lists.newArrayList(mockAddressCache.get(tn));
	}

	@Override
	public List<Address> getPendingAddressChanges(String tn) throws VendorException {
		return Lists.newArrayList(mockAddressCache.get(tn));
	}

	@Override
	public void changeAddress(String tn, Address address) throws VendorException {
		mockAddressCache.put(tn, address);

	}

	/**
	 * Internal method to create a single address for testing.
	 * 
	 * @return
	 */
	@SuppressWarnings("serial")
	private static Map<String, Address> createAddressCache() {

		final Address address1 = new Address();
		address1.setHouseNumber("123");
		address1.setStreet("Test Street 1");
		address1.setCity("Houston");
		address1.setState("TX");
		address1.setZip("75042");

		return new ConcurrentHashMap<String, Address>() {
			{
				put("1234567890", address1);
			}
		};
	}

}