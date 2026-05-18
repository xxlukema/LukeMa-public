/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.broadsoft.cpbx.e911.api;

/**
 *
 * @author chris
 */
public class VendorException extends Exception {

    /**
	 * Serialization Id. 
	 */
	private static final long serialVersionUID = -3549819297645942466L;
	
	private int responseCode;
    
    public VendorException() {
    }

    public VendorException(String message) {
        super(message);
    }


    public VendorException(Throwable cause) {
        super(cause);
    }
    
    public VendorException(int responseCode, String message) {
        this(message);
        this.responseCode = responseCode;
    }

    public VendorException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Getter for the response code.
     * @return
     */
    public int getResponseCode() {
    	return responseCode;
    }
    
    /**
     * Setter for the response code.
     * @param responseCode
     */
    public void setResponseCode(int responseCode) {
    	this.responseCode = responseCode;
    }
}
