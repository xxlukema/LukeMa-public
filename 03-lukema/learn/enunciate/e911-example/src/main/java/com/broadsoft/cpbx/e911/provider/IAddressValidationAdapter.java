/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.provider;

import com.broadsoft.cpbx.e911.api.AddressValidationException;
import com.broadsoft.cpbx.e911.domain.Address;

/**
 *
 * @author chris
 */
public interface IAddressValidationAdapter {
    
    /**
     * 
     * @param address
     * @return
     */
    Address validateAddress(Address address) throws AddressValidationException;
}
