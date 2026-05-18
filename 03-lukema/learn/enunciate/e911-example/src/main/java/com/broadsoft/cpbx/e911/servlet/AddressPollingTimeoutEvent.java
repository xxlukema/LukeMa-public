package com.broadsoft.cpbx.e911.servlet;

/**
 * This event can be fired when a timeout occurs while waiting for
 * an address change.
 * 
 * @author chris
 */
public class AddressPollingTimeoutEvent {

	private String tn;
	
	public AddressPollingTimeoutEvent() {
	}
	
	public AddressPollingTimeoutEvent(final String tn) {
		this.tn = tn;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}
}
