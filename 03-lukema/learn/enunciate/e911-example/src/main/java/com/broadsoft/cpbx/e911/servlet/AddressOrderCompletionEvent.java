package com.broadsoft.cpbx.e911.servlet;

/**
 * This event is fired when an address goes into the active state.
 * 
 * @author chris
 */
public class AddressOrderCompletionEvent {

	private String tn;
	
	public AddressOrderCompletionEvent(String tn) {
		this.tn = tn;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}
}
