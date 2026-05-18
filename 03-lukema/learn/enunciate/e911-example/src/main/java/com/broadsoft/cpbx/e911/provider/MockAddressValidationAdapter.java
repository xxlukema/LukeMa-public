package com.broadsoft.cpbx.e911.provider;

import com.broadsoft.cpbx.e911.api.AddressValidationException;
import com.broadsoft.cpbx.e911.domain.Address;

public class MockAddressValidationAdapter implements IAddressValidationAdapter {

	@Override
	public Address validateAddress(Address address) throws AddressValidationException {
		return address;
	}
}
