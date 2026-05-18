/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.api;

import java.util.Collection;
import java.util.List;

import com.broadsoft.cpbx.e911.domain.Address;

/**
 * The main interface for the address change service.
 * @author chris
 */

public interface IAddressChangeService {

    /**
     * Used to fetch the current address from the service provider.
     * 
     * This will return the {@link Address} object with the current
     * address information for telephone number.
     * 
     * @param tn The customer telephone number
     * @return The Address object
     */
    Address getCurrentAddress(String tn) throws Exception;

    
    /**
     * Used to fetch the primary address of the tn. This may be the same as the
     * current address if there is a current e911 address configured. The vendor
     * should maintain different values for the primary address and the current address.
     * This way the user has the ability to reset an address back to the default address.
     * 
     * @param tn The telephone number used to look up the primary address.
     * @return The primary address registered for tn
     * @throws VendorException If an error occurrs on the vendor implemenation.
     * @throws InvalidTNException if the telephone number is invalid
     */
    Address getPrimaryAddress(String tn) throws VendorException, InvalidTNException;
    
    /**
     * This will retrieve the previous address changes. This is completely up to
     * the vendor to implement. If it is not implemented by the vendor then a 
     * {@link UnsupportedOperationException} will be thrown. This can be used
     * by the ui to aid in selecting a previous address when changing to a 
     * new location.
     * 
     * @param tn The telephone number to use for fetching the addresses
     * @return Collection of addresses
     * @throws InvalidTNException If the telephone number is invalid.
     */
    List<Address> getPreviousAddresses(String tn) throws InvalidTNException;

    /**
     * This is used to fetch any address changes that are pending in the system.
     * This is in place because some vendors may have an address in a state before it
     * becomes active. This is the case with Verizon. If the vendor does not support
     * this method then an {@link UnsupportedOperationException} may be thrown.
     * 
     * Verizon supports having multiple open orders and some can be in Pending, or 
     * Pending_INVSTIGATION state, so this method returns all those orders that are in the
     * pending state. Our implementation should only return 1 address ever for this. It is 
     * a list because we can't know if an adddress has been set for in the pending state from
     * another api but by default there can be only one address configured at a time.
     * 
     * @param tn The telephone number to use to look up. 
     * @return List of addresses that are in the pending state.
     * @throws InvalidTNException 
     */
    List<Address> getPendingAddressChanges(String tn) throws InvalidTNException;
    
    /**
     * This validates an address, this is not required be default but is a vendor specific
     * way to validate that an address will be accepted when changing an address. The change
     * address function in the implementor should also handle validating the address before
     * submitting an address change. This method returns an {@link AddressValidationResponse} which
     * may contain alternate addresses that match the criteria specified. This must be implemented
     * by the vendor when an address validation occurrs. The address validation response will
     * contain a list of hints that can be used to let the user select a valid address.
     * 
     * @param address The address to validate.
     * @throws InvalidTNException 
     * @return The address validation resopnse
     */
    AddressValidationResponse validateAddress(String tn, Address address) throws InvalidTNException;

    /**
     * Create a change order for a single address. The {@link AddressChangeRequest} will contain
     * the tn and the requested address to change. 
     * 
     * @param request The address change request that contains phone number and requested address
     * @throws VendorException If a Vendor error occurrs.
     * @throws InvalidTNException If the tn in the {@link AddressChangeRequest} is invalid.
     */
    Response changeAddressRequest(AddressChangeRequest request) throws VendorException, InvalidTNException;
    
    /**
     * Helper method if you do not want to construct an {@link AddressChangeRequest}. 
     * 
     * @param tn The telephone number for the request.
     * @param address The address to configure as the new address.
     * @throws InvalidTNException If the tn is invalid 
     * @return
     * 
     * @see #changeAddressRequest(AddressChangeRequest)
     */
    @Deprecated
    Response changeAddress(String tn, Address address) throws VendorException, InvalidTNException;

    /**
     * Helper to request multiple address changes. This was implemented so that internal services could
     * also request to have multiple addresses changed at once for efficiency. This will effectively call
     * {@link #changeAddress(String, Address)} for all the requests contained in the request collection.
     * 
     * @see #changeAddressRequest(AddressChangeRequest)
     * 
     * @param requests Collection of address change requests based on phone number and address
     * @throws VendorException 
     */
    CompositeResponse<Response> changeAddresses(Collection<AddressChangeRequest> requests) throws VendorException;

    /**
     * Reset a single address to it's primary address. This is required to be implemented by the
     * vendor as we will not keep up with primary addresses.
     * 
     * @param tn The telephone number of the address to reset.
     * @throws InvalidTNException If the telephone number is invalid
     */
    AddressResetResponse resetAddress(String tn) throws InvalidTNException;

    /**
     * Reset a list of addresses by the telephone numbers. This is an internal
     * method and is exposed for internal services. Customers will typically
     * not be using this method. It can be left unimplemented.
     * 
     * @param tns Collection of telephone numbers to reset
     */
    CompositeResponse<AddressResetResponse> resetAddresses(Collection<String> tns);

    /**
     * This method allows the user to cancel a currently open address change order. In
     * some cases the address change make take quite some time due to synchronization issues.
     * In this case an order may be open by the vendor while the synchronization is occurring.
     * This gives the user the ability to cancel that currently open order if need be.
     * 
     * @throws InvalidTNException If the telephone number is invalid. 
     */
    Response cancelAddressChange(String tn) throws InvalidTNException;
}