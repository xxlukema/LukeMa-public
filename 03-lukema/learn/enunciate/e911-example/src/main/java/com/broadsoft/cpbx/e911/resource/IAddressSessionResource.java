/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.broadsoft.cpbx.e911.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.broadsoft.cpbx.e911.domain.AddressChangeSession;


/**
 *
 * @author chris
 */
public interface IAddressSessionResource {
   /**
     * This is used to initiate a new client request. The client request
     * will have the url to redirect to along with a another url for checking
     * status of the request.
     * @param Telephone number of the requested change
     * @return
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    public AddressChangeSession createSession(@QueryParam(value = "tn") String tn);
    
}
