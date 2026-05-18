package com.broadsoft.cpbx.e911.api;

import java.util.List;

import com.broadsoft.cpbx.e911.domain.Address;

public class AddressValidationException extends VendorException {

	/**
	 * Serialization Id
	 */
	private static final long serialVersionUID = -1150103956102614275L;

	
	private List<Address> hints;
	
	public AddressValidationException() {
		super();
	}

	public AddressValidationException(int responseCode, String message) {
		super(responseCode, message);
	}

	public AddressValidationException(String message, Throwable cause) {
		super(message, cause);
	}

	public AddressValidationException(String message) {
		super(message);
	}

	public AddressValidationException(Throwable cause) {
		super(cause);
	}

	public List<Address> getHints() {
		return hints;
	}

	public void setHints(List<Address> hints) {
		this.hints = hints;
	}
}
