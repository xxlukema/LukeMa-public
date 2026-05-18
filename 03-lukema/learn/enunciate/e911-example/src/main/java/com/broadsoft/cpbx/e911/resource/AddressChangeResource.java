package com.broadsoft.cpbx.e911.resource;


import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.Path;

import com.broadsoft.cpbx.e911.api.AddressChangeRequest;
import com.broadsoft.cpbx.e911.api.AddressChangeRequests;
import com.broadsoft.cpbx.e911.api.AddressResetRequests;
import com.broadsoft.cpbx.e911.api.AddressResetResponse;
import com.broadsoft.cpbx.e911.api.AddressValidationResponse;
import com.broadsoft.cpbx.e911.api.CompositeResponse;
import com.broadsoft.cpbx.e911.api.IAddressChangeService;
import com.broadsoft.cpbx.e911.api.IAddressChangeServiceResource;
import com.broadsoft.cpbx.e911.api.InvalidTNException;
import com.broadsoft.cpbx.e911.api.Response;
import com.broadsoft.cpbx.e911.api.VendorException;
import com.broadsoft.cpbx.e911.domain.Address;
import com.broadsoft.cpbx.e911.domain.Directional;
import com.broadsoft.cpbx.e911.domain.State;
import com.broadsoft.cpbx.e911.domain.Thoroughfare;
import com.broadsoft.cpbx.e911.domain.WebSession;


/**
 * The address change resource exposes the rest methods that allow you to 
 * get/ change / reset / validate an address. It is a generic implementation in that
 * it passes the requests onto the IAddressChangeService that is injected at runtime.
 * 
 * All methods handle simple validation of the telephone number in that they
 * are required to be 10 digits. 
 * 
 * @author chris
 */
@Path("/address")
public class AddressChangeResource
    implements IAddressChangeServiceResource {

    /**
     * The delegated service that handles the requests.
     */
    private IAddressChangeService delegate;

    /**
     * Injected contructor that initializes the {@link IAddressChangeService}
     * 
     * @param delegate
     */
    @Inject
    public AddressChangeResource(IAddressChangeService delegate) {
        this.delegate = delegate;
    }

    /**
     * {@inheritDoc}
     */
    public Address getCurrentAddress(String tn)
        throws Exception {
        validateTn(tn);
        WebSession.getInstance().setTn(tn);
        if (tn.length() != 10) {
            throw new IllegalArgumentException("ERR_INVALID_PHONE_NUMBER");
        }
        return delegate.getCurrentAddress(tn);
    }

    /**
     * {@inheritDoc}
     */
    public Address getPrimaryAddress(String tn)
        throws VendorException, InvalidTNException {
        validateTn(tn);
        WebSession.getInstance().setTn(tn);
        return delegate.getPrimaryAddress(tn);
    }

    /**
     * {@inheritDoc}
     */
    public List<Address> getPreviousAddresses(String tn)
        throws InvalidTNException {
        validateTn(tn);
        WebSession.getInstance().setTn(tn);
        return delegate.getPreviousAddresses(tn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Address> getPendingAddressChanges(String tn)
        throws InvalidTNException {
        validateTn(tn);
        WebSession.getInstance().setTn(tn);
        return delegate.getPendingAddressChanges(tn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressValidationResponse validateAddress(String tn, Address address)
        throws InvalidTNException {
        validateTn(tn);
        WebSession.getInstance().setTn(tn);
        return delegate.validateAddress(tn, address);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response changeAddressRequest(AddressChangeRequest request)
        throws VendorException, InvalidTNException {
        return changeAddress(request.getTn(), request.getAddress());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response changeAddress(String tn, Address address)
        throws VendorException, InvalidTNException {
        validateTn(tn);
        WebSession.getInstance().setTn(tn);
        return delegate.changeAddress(tn, address);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response changeAddressForm(
            String tn,
            String streetNumber,
            String streetName,
            String predirection,
            String streetType,
            String city,
            String zip,
            String unit,
            String state,
            String country,
            String reset,
            String acknowlege)
        throws VendorException, InvalidTNException {
        validateTn(tn);
        WebSession.getInstance().setTn(tn);

        Address address = new Address();
        address.setHouseNumber(streetNumber);
        address.setStreet(streetName);
        address.setUnitNumber(unit);
        address.setCity(city);
        address.setState(state);
        address.setZip(zip);
        address.setCountry(country);

        AddressChangeRequest changeRequest = new AddressChangeRequest(tn, address);
        return delegate.changeAddressRequest(changeRequest);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompositeResponse<Response> changeAddresses(AddressChangeRequests requests)
        throws VendorException {
        return delegate.changeAddresses(requests.getRequests());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AddressResetResponse resetAddress(String tn)
        throws InvalidTNException {
        validateTn(tn);
        WebSession.getInstance().setTn(tn);
        return delegate.resetAddress(tn);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompositeResponse<AddressResetResponse> resetAddresses(AddressResetRequests requests) {
        return resetAddresses(requests.getTns());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response cancelAddressChange(String tn)
        throws InvalidTNException {
        validateTn(tn);
        WebSession.getInstance().setTn(tn);
        return delegate.cancelAddressChange(tn);
    }

    /**
     * Used to inject the actual service delegate.
     * @param service
     */
    @Inject
    public void setAddressChangeService(IAddressChangeService service) {
        this.delegate = service;
    }

    /**
     * {@inheritDoc}
     * 
     * This method is not exposed in the service but are required to implement
     * the {@link IAddressChangeService} implementation Jersey does not support
     * sending non annotated collections so they are wrapped in the
     * {@link AddressChangeRequests} object wrapper to handle json
     * serialization.
     * 
     * @see #changeAddresses(java.lang.String,
     *      com.broadsoft.appia.e911address.api.adpt_tech.rialto.appia.b2b.vzvv911changeorder.server.service.domain.AddressChangeRequests)
     * @param requests
     * @return
     * @throws VendorException
     */
    @Override
    public CompositeResponse<Response> changeAddresses(Collection<AddressChangeRequest> requests)
        throws VendorException {
        return delegate.changeAddresses(requests);
    }

    /**
     * This method is not exposed in the service but are required to implement
     * the {@link IAddressChangeService} implementation Jersey does not support
     * sending non annotated collections so they are wrapped in the
     * {@link AddressReqestRequests} object wrapper to handle json
     * serialization.
     * 
     * @see #resetAddresses(java.lang.String,
     *      com.broadsoft.appia.e911address.api.adpt_tech.rialto.appia.b2b.vzvv911changeorder.server.service.domain.AddressResetRequests)
     * @param tns
     * @return
     */
    @Override
    public CompositeResponse<AddressResetResponse> resetAddresses(Collection<String> tns) {
        return delegate.resetAddresses(tns);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<State> listStates() {
        return Arrays.asList(State.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Thoroughfare> listThoroughfares() {
        return Arrays.asList(Thoroughfare.values());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Directional> listDirectional() {
        return Arrays.asList(Directional.values());
    }

    public static void validateTn(String toValidate)
        throws InvalidTNException {
        if ((toValidate.length() != 10) || !(toValidate.matches("\\d*"))) {
            throw new InvalidTNException("Invalid TN " + toValidate);
        }
    }
}
