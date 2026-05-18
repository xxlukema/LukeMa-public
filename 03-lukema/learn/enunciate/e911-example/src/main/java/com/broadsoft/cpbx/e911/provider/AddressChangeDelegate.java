/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.provider;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.broadsoft.cpbx.e911.api.AddressChangeRequest;
import com.broadsoft.cpbx.e911.api.AddressResetResponse;
import com.broadsoft.cpbx.e911.api.AddressValidationException;
import com.broadsoft.cpbx.e911.api.AddressValidationResponse;
import com.broadsoft.cpbx.e911.api.CompositeResponse;
import com.broadsoft.cpbx.e911.api.IAddressChangeService;
import com.broadsoft.cpbx.e911.api.Response;
import com.broadsoft.cpbx.e911.api.VendorException;
import com.broadsoft.cpbx.e911.domain.Address;
import com.broadsoft.cpbx.e911.servlet.StatusEvent;
import com.google.common.eventbus.EventBus;


/**
 * 
 * @author chris
 */
public class AddressChangeDelegate
    implements IAddressChangeService {

    /**
     * Class logger
     */
    private static final Logger logger = LogManager.getLogger(AddressChangeDelegate.class);
    /**
     * Vendor mechanism to implement address changes.
     */
    private IAddressChangeAdapter vendorAdapter;
    /**
     * Adapter to support address validation.
     */
    private IAddressValidationAdapter addressValidator;

    /**
     * The eventbus used to register for events.
     */
    private EventBus eventBus;

    @Inject
    public AddressChangeDelegate(IAddressChangeAdapter vendorAdapter, IAddressValidationAdapter addessValidator, EventBus eventBus) {
        this.vendorAdapter = vendorAdapter;
        this.addressValidator = addessValidator;
        this.eventBus = eventBus;
    }

    /**
     * {@inheritDoc}
     * 
     * Uses the vendor adapter to fetch the current address.
     * @throws VendorException 
     */
    @Override
    public Address getCurrentAddress(String tn)
        throws VendorException {
        if (tn == null) {
            throw new IllegalArgumentException("tn is a required field");
        }

        Address address = null;
        try {
            address = vendorAdapter.getCurrentAddress(tn);
        } catch (VendorException e) {
            logger.error("Vendor exception during getCurrentAddress for tn " + tn, e);
            throw e;
        }
        return address;
    }

    public Address getPrimaryAddress(String tn)
        throws VendorException {
        if (tn == null) {
            throw new IllegalArgumentException("tn is a required field");
        }

        Address address = null;

        try {
            address = vendorAdapter.getPrimaryAddress(tn);
        } catch (VendorException e) {
            logger.error("Vendor exception during getPrimaryAddress for tn " + tn, e);
            throw e;
        }
        return address;
    }

    @Override
    public List<Address> getPendingAddressChanges(String tn) {
        if (tn == null) {
            throw new IllegalArgumentException("tn is a required field");
        }

        List<Address> address = null;

        try {
            address = vendorAdapter.getPendingAddressChanges(tn);
        } catch (VendorException e) {
            logger.error("Vendor exception during getPrimaryAddress for tn " + tn, e);
        }
        return address;
    }

    public List<Address> getPreviousAddresses(String tn) {
        if (tn == null) {
            throw new IllegalArgumentException("tn is a required field");
        }
        List<Address> previousAddresses = null;

        try {
            previousAddresses = vendorAdapter.getPreviousAddresses(tn);
        } catch (VendorException e) {
            logger.error("Vendor exception during cancelAddressChange for tn " + tn, e);
        }

        return previousAddresses;
    }

    /**
     * This method will first look up the Customer by their
     * 
     * {@inheritDoc}
     */
    @Override
    public AddressResetResponse resetAddress(final String tn) {
        AddressResetResponse response = new AddressResetResponse();

        try {
            eventBus.post(StatusEvent.create(tn, StatusEvent.ADDRESS_RESET_SUBMITTED, null));
            vendorAdapter.resetAddress(tn);

            Address currentAddress = getCurrentAddress(tn);
            response.setCurrentAddress(currentAddress);

            eventBus.post(StatusEvent.create(tn, StatusEvent.SUCCESS, null));
        } catch (VendorException e) {
            logger.error("Exception", e);
            response.setStatus(Response.FAILURE);
            response.setErrorMessage(e.getMessage());
        }

        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CompositeResponse<AddressResetResponse> resetAddresses(Collection<String> tns) {
        CompositeResponse<AddressResetResponse> compositeResponse = new CompositeResponse<AddressResetResponse>();

        if (tns == null) {
            String errMsg = "ERR_MISSING_ARGUMENT";
            throw new IllegalArgumentException(String.format(errMsg, "tns"));
        }

        for (String tn : tns) {
            AddressResetResponse operationResponse = resetAddress(tn);
            compositeResponse.add(operationResponse);
        }

        return compositeResponse;
    }

    @Override
    public Response cancelAddressChange(final String tn) {
        if (tn == null) {
            throw new IllegalArgumentException("tn is a required field");
        }

        Response response = new Response();

        logger.error("Cancelling address change for {0}", tn);
        try {
            eventBus.post(StatusEvent.create(tn, StatusEvent.ADDRESS_CHANGE_CANCEL_SUBMITTED, null));
            vendorAdapter.cancelAddressChange(tn);
            eventBus.post(StatusEvent.create(tn, StatusEvent.SUCCESS, null));
        } catch (VendorException e) {
            logger.error("Vendor exception during cancelAddressChange for tn " + tn, e);
            eventBus.post(StatusEvent.create(tn, StatusEvent.VENDOR_ERROR, e.getMessage()));
        }

        return response;
    }

    /**
     * {@inheritDoc}
     * 
     * The {@link IAddressChangeAdapter} implementation will throw an
     * {@link VendorException} if there is a problem submitting the change
     * address request.
     * 
     * This method delegates to the {@link #changeAddress(String, Address)} to handle
     * the request, the {@link #changeAddress(String, Address)} method will handle firing
     * the events on the event bus to alert the listening clients like btbc of an issue
     * 
     * The exceptions from {@link #changeAddress(String, Address)} will be propagated back up
     * to the client handler and in most cases this is the rest handler.
     * @throws VendorException If there is any issue change the address with the supplied
     * adapter.
     */
    @Override
    public Response changeAddressRequest(AddressChangeRequest request)
        throws VendorException {

        if (request == null) {
            String errMsg = "ERR_MISSING_ARGUMENT";
            throw new IllegalArgumentException(String.format(errMsg, "request"));
        }

        return changeAddress(request.getTn(), request.getAddress());
    }

    /**
     * {@inheritDoc}
     * 
     * This will iterate through the list of requests and submit them
     * sequentially to the underlying {@link IAddressChangeAdapter} vendor
     * adapter to complete the address change.
     * 
     * @return CompositeResponse contains a response for each address change
     *         request that was submitted. If the composite response is set to
     *         failed you will have one or more failures within the list of
     *         responses.
     * @throws VendorException If there is an issue change the address with the
     * supplied adapter.
     */
    @Override
    public CompositeResponse<Response> changeAddresses(Collection<AddressChangeRequest> requests)
        throws VendorException {
        CompositeResponse<Response> compositeResponse = new CompositeResponse<Response>();

        if (requests == null) {
            String errMsg = "ERR_MISSING_ARGUMENT";
            throw new IllegalArgumentException(String.format(errMsg, "requests"));
        }

        for (AddressChangeRequest request : requests) {
            Response operationResponse = changeAddressRequest(request);
            compositeResponse.add(operationResponse);
        }

        return compositeResponse;
    }

    /**
     * Internal implementation that will submit the address change to the vendor
     * adapter and check status on the response. This will also send events to
     * the listening client to alert on status.
     * 
     * @param tn The telephone number of the address to change
     * @param altAddress The alternate address to change to
     */
    public Response changeAddress(String tn, Address altAddress)
        throws VendorException {

        try {
            eventBus.post(StatusEvent.create(tn, StatusEvent.ADDRESS_CHANGE_SUBMITTED, null));

            vendorAdapter.changeAddress(tn, altAddress);

            eventBus.post(StatusEvent.create(tn, StatusEvent.SUCCESS, null));
        } catch (VendorException e) {
            eventBus.post(StatusEvent.create(tn, StatusEvent.VENDOR_ERROR, e.getMessage()));
            throw e;
        }
        return new Response(Response.SUCCESS);
    }

    /**
     * {@inheritDoc}
     * 
     * This method will notify the client when an invalid address was entered.
     * The client doesn't have to take any action it is purely for notification.
     * 
     * @param address
     * @return
     */
    @Override
    public AddressValidationResponse validateAddress(String tn, Address address) {
        AddressValidationResponse response = new AddressValidationResponse();

        try {
            Address validatedAddress = addressValidator.validateAddress(address);
            response.setValid(true);
            List<Address> hints = new ArrayList<Address>();
            hints.add(validatedAddress);
            response.setHints(hints);
            response.setStatus(AddressValidationResponse.SUCCESS);
        } catch (AddressValidationException e) {
            response.setStatus(Response.FAILURE);
            response.setValid(false);
            response.setHints(e.getHints());
        }

        StatusEvent event = null;

        if (response.getStatus() == AddressValidationResponse.FAILURE) {
            event = StatusEvent.create(tn, StatusEvent.ADDRESS_VALIDATION_ERROR, response.getErrorMessage());
        } else {
            event = StatusEvent.create(tn, StatusEvent.ADDRESS_VALIDATION_SUCCESS, null);
        }

        eventBus.post(event);
        return response;
    }
}
