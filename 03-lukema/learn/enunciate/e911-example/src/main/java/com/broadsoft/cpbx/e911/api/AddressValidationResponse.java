/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.api;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.broadsoft.cpbx.e911.domain.Address;

/**
 *
 * @author chris
 */
@XmlRootElement
public class AddressValidationResponse extends Response {

	/**
	 * List of address hints in the case that the address was not found but a
	 * close approximation was found.
	 */
	private List<Address> hints;

	private boolean isValid;

	public AddressValidationResponse() {
		this(SUCCESS);
	}

	/**
	 * @param success
	 */
	public AddressValidationResponse(int success) {
		this(success, null, null);
	}

	public AddressValidationResponse(int success, String message) {
		this(success, message, null);
	}

	public AddressValidationResponse(int success, String errorMessage, List<Address> hints) {
		super(success, errorMessage);
		this.hints = hints;
	}

	public List<Address> getHints() {
		return hints;
	}

	public void setHints(List<Address> hints) {
		this.hints = hints;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
}
