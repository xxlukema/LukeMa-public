/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.provider;

import java.util.List;

import org.jvnet.hk2.annotations.Contract;

import com.broadsoft.cpbx.e911.api.IAddressChangeService;
import com.broadsoft.cpbx.e911.api.VendorException;
import com.broadsoft.cpbx.e911.domain.Address;

/**
 *
 * @author chris
 */
@Contract
public interface IAddressChangeAdapter {

    /**
     * Used to fetch the current address from the service provider.
     * In the case of Verizon this will retrieve the address from the
     * Intrada database.
     * @param tn The customer telephone number
     * @return The AlternateAddress object.
     */
    Address getCurrentAddress(String tn) throws VendorException;
    

    /**
     * Used to fetch the primary address for the telephone number.
     * @param tn
     * @return
     * @throws VendorException
     */
	Address getPrimaryAddress(String tn) throws VendorException;
    
    /**
     * The vendor will supply a way to fetch previous addresses.
     * @param tn 
     * @return
     * @throws VendorException
     */
    List<Address> getPreviousAddresses(String tn) throws VendorException;

    /**
     * Fetch previous address with an optional argument of the count for total number of 
     * addresses to fetch.
     * 
     * @param tn The telephone number to search on
     * @param count Number of previous addresses to fetch.
     * @return The List of addresses found for the tn
     * @throws VendorException
     */
	List<Address> getPreviousAddresses(String tn, int count) throws VendorException;

    
    /**
     * Used to fetch pending address changes for the user.
     * @see IAddressChangeService#getPendingAddressChanges(String)
     * @param tn
     * @return
     */
	List<Address> getPendingAddressChanges(String tn) throws VendorException;
    
    /**
     * Change a single address
     * @param encodedToken The encoded status token used for notification events.
     * @param request The address change request that contains phone number and requested address
     */
    void changeAddress(String tn, Address address) throws VendorException;

    /**
     * Reset a single address.
     * @param encodedToken The encoded status token used for notification events.
     * @param tn The telephone number of the address to reset.
     */
    void resetAddress(String tn) throws VendorException;

    /**
     * This method is exposed so that we can collect when a user cancel's the
     * form on the myphone site.
     */
    void cancelAddressChange(String tn) throws VendorException;
}
