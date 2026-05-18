/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.api;

import java.util.Collection;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * This is a simple request wa
 * @author chris
 */
@XmlRootElement
public class AddressChangeRequests {
    
    private Collection<AddressChangeRequest> requests;

    public AddressChangeRequests() {
        requests = new HashSet<AddressChangeRequest>();
    }
    
    public AddressChangeRequests(Collection<AddressChangeRequest> requests) {
        this.requests = requests;
    }
    
    public Collection<AddressChangeRequest>  getRequests() {
        return requests;
    }

    public void addRequest(AddressChangeRequest request) {
        this.requests.add(request);
    }
}
