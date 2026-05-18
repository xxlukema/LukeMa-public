/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.broadsoft.cpbx.e911.servlet;

import java.io.Serializable;

/**
 * A status event is a notification that can be sent to the listening client to
 * report status upon a certain event happening.
 * 
 * @author chris
 */
public class StatusEvent implements Serializable {

	/**
	 * Serialization Id
	 */
	private static final long serialVersionUID = 5134249943069970745L;

	/**
	 * Success for the submitted operation.
	 */
	public static final int SUCCESS = 0;

	/**
	 * Failure occurred while submitting the request to change address. This is
	 * not a problem with the vendor api
	 */
	public static final int FAILURE = 1;

	/**
	 * If the status of the order is in pending state this event will be
	 * triggered. This is equivilent to the vz pending error or pending
	 * investigation.
	 */
	public static final int PENDING = 2;

	/**
	 * If there is a timeout while waiting for the address to go active this
	 * event will be thrown.
	 */
	public static final int POLLING_TIMEOUT = 3;

	/**
	 * The user requested to cancel the address update.
	 */
	public static final int USER_CANCEL = 5;

	/**
	 * The operation sent to the vendor reported success
	 */
	public static final int VENDOR_SUCCESS = 10;

	/**
	 * A general error occurred when updating address with vendor. Check the
	 * message for more information
	 */
	public static final int VENDOR_ERROR = 11;

	/**
	 * Notification: that the request was submitted
	 */
	public static final int ADDRESS_CHANGE_SUBMITTED = 15;

	/**
	 * Notification: that an address reset was submitted.
	 */
	public static final int ADDRESS_RESET_SUBMITTED = 16;

	/**
	 * Notification: that an address change cancel was submitted.
	 */
	public static final int ADDRESS_CHANGE_CANCEL_SUBMITTED = 17;

	/**
	 * Notification: that the user input an incorrect address.
	 */
	public static final int ADDRESS_VALIDATION_SUCCESS = 18;

	/**
	 * Notification: that the user input an incorrect address.
	 */
	public static final int ADDRESS_VALIDATION_ERROR = 19;

	/**
	 * Notification that the token has expired and it has been removed from the
	 * list of valid tokens.
	 */
	public static final int TOKEN_EXPIRED = 30;

	/**
	 * Integer status for the event from one of the status codes above.
	 */
	private Integer status;

	/**
	 * Status message tied with the event.
	 */
	private String message;

	private String tn;

	/**
	 * Public event construct use the create though. This is only here for the
	 * json serializer.
	 */
	public StatusEvent() {
	}

	/**
	 * Constructor that will set the status of the event along with the message
	 * 
	 * @param status
	 * @param message
	 */
	public StatusEvent(String tn, Integer status, String message) {
		this.tn = tn;
		this.status = status;
		this.message = message;
	}

	/**
	 * Sets the session token that this event is created for.
	 * 
	 * @param tn
	 *            Telephone number
	 */
	public void setTn(String tn) {
		this.tn = tn;
	}

	/**
	 * Get the session token that this event is created for.
	 * 
	 * @return
	 */
	public String getTn() {
		return tn;
	}

	/**
	 * Get the message for the event
	 * 
	 * @return
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Set the message for the event
	 * 
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get the status of the event
	 */
	public Integer getStatusCode() {
		return status;
	}

	/**
	 * Setter for the status of the event
	 * 
	 * @param status
	 */
	public void setStatusCode(Integer status) {
		this.status = status;
	}

	public static StatusEvent create(String tn, Integer status, String message) {
		if (tn == null) {
			throw new IllegalArgumentException("Param tn is required");
		}

		return new StatusEvent(tn, status, message);
	}
}