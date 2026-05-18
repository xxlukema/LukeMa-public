package com.broadsoft.cpbx.e911.servlet;


/**
 * This event is fired when a new order is submitted and goes into the pending status.
 * 
 * @author chris
 */
public class AddressPendingEvent {

	private String tn;
	
	private int orderId;
	
	private String externalCustomerId;
	
	public AddressPendingEvent(String tn, int orderId, String externalCustomerId) {
		this.tn = tn;
		this.orderId = orderId;
	}

	public String getTn() {
		return tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getExternalCustomerId() {
		return externalCustomerId;
	}
}
