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
public class AddressChangeRequest {

    private String tn;

    private Address address;
    
    public AddressChangeRequest() {
    }

    public AddressChangeRequest(String tn, Address address) {
        this.tn = tn;
        this.address = address;   
    }
    
    public Address getAddress() {
        return address;
    }

	public void setAddress(Address address) {
		this.address = address;
    }

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }
}
