/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.api;

import javax.xml.bind.annotation.XmlRootElement;

import com.broadsoft.cpbx.e911.domain.Address;

/**
 *
 * @author chris
 */
@XmlRootElement
public class AddressResetResponse extends Response {

    private Address currentAddress;
    
    
    public AddressResetResponse() {
    }

    public AddressResetResponse(int success) {
        super(success);
    }
    
    public AddressResetResponse(int success, Address currentAddress) {
        this(success);
        this.currentAddress = currentAddress;
    }

    public AddressResetResponse(int success, String errorMessage) {
        super(success, errorMessage);
    }
    
    public Address getCurrentAddress() {
        return currentAddress;
    }
    
    public void setCurrentAddress(Address currentAddress) {
        this.currentAddress = currentAddress;
    }
}
