package com.broadsoft.cpbx.e911.resource;

import javax.inject.Inject;
import javax.ws.rs.Path;

import com.broadsoft.cpbx.e911.domain.AddressChangeSession;

/**
 * This is a rest service that allows a client to make an HTTP get to fetch
 * a token used for updating an address and collecting status from the operation.
 * 
 * @author chris
 */
@Path("/session_request")
public class AddressSessionResource implements IAddressSessionResource {

	private final AddressChangeSession session;
	
	@Inject
    public AddressSessionResource(AddressChangeSession session) {
		this.session = session;
    }

    /**
     * {@inheritDoc}
     *
     * @param tn The telephone number of account that you want to change
     * the address of.
     * @return
     */
    public AddressChangeSession createSession(String tn) {
        if (tn == null) {
            throw new IllegalArgumentException("tn is a required argument");
        }

        session.setTn(tn);
        return session;
    }
}