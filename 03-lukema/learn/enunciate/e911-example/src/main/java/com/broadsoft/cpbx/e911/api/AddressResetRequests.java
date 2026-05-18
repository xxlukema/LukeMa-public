/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.api;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author chris
 */
@XmlRootElement
public class AddressResetRequests {

    private Collection<String> tns = null;

    public AddressResetRequests() {
        tns = new HashSet<String>();
    }
    
    public AddressResetRequests(Collection<String> tns) {
        this.tns = tns;
    }

    public AddressResetRequests(String[] tns) {
        this.tns = Arrays.asList(tns);
    }

    public Collection<String> getTns() {
        return tns;
    }

    public void addTn(String tn) {
        this.tns.add(tn);
    }
}
