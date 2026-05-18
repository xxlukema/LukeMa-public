package com.broadsoft.cpbx.e911.api;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chris
 */
@XmlRootElement
public class Response {
    
	/**
	 * Successful request
	 */
    public static final int SUCCESS = 0;

    /**
     * Failure, see {@link #errorMessage} for details
     */
    public static final int FAILURE = 1;
    
    /**
     * This operation is not supported by this vendor.
     */
    public static final int UnsupportedOperationException = 3;
    
    private int status = 0;

    private String errorMessage;

    public Response() {
        this(SUCCESS, null);
    }
    
    public Response(int success) {
        this(success, null);
    }
    
    public Response(int success, String errorMessage) {
        this.status = success;
        this.errorMessage = errorMessage;
    }
    
    public int getStatus() {
        return status;
    }

    public void setStatus(int success) {
        this.status = success;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
    
    public static Response success() {
    	return new Response(Response.SUCCESS);
    }
    
    public static Response response(int value) {
    	return new Response(value);
    }
}
