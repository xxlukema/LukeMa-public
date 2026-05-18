/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.broadsoft.cpbx.e911.api;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.broadsoft.cpbx.e911.domain.Address;
import com.broadsoft.cpbx.e911.domain.Directional;
import com.broadsoft.cpbx.e911.domain.State;
import com.broadsoft.cpbx.e911.domain.Thoroughfare;

/**
 * This simply annotates the address change service with it's required jax-ws
 * annotations. Since jax-ws requires an interface and an implementation.
 *
 * @author chris
 */
public interface IAddressChangeServiceResource extends IAddressChangeService {

    /**
     * {@inheritDoc}
     * 
     * <pre>
     * 
     * <b>Json</b>
     * <code>
     * curl -H "Accept: application/json" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/address/1234567890
     * 
     * {"houseNumber":"123","street":"Test Street 1","thoroughfare":null,"unitNumber":null,"preDirection":null,"postDirection":null,"city":"Houston","state":"TX","zip":"75042","unit":null,"country":null}
     * </code>
     * 
     * <b>XML</b>
     * {@code
     * curl -H "Accept: application/xml" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/address/1234567890
     * 
     * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<address>
     * 		<city>Houston</city>
     * 		<houseNumber>123</houseNumber>
     * 		<state>TX</state>
     * 		<street>Test Street 1</street>
     * 		<zip>75042</zip>
     * 	</address>
     * }
     * </pre>
     */
    @GET
    @Path("/address/{tn}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Override
    public Address getCurrentAddress(@PathParam("tn") String tn) throws Exception;
    
    /**
     * {@inheritDoc}
     * 
     * 
     * 
     * <pre>
     * 
     * <b>Json</b>
     * <code>
     * curl -H "Accept: application/json" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/primary_address/1234567890
     * 
     * {"houseNumber":"123","street":"Test Street 1","thoroughfare":null,"unitNumber":null,"preDirection":null,"postDirection":null,"city":"Houston","state":"TX","zip":"75042","unit":null,"country":null}
     * </code>
     * 
     * <b>XML</b>
     * <code>
     * curl -H "Accept: application/xml" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/primary_address/1234567890
     * 
     * {@code
     * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<address>
     * 		<city>Houston</city>
     * 		<houseNumber>123</houseNumber>
     * 		<state>TX</state>
     * 		<street>Test Street 1</street>
     * 		<zip>75042</zip>
     * 	</address>
     * </code>
     * }
     * </pre>
     */
    @GET
    @Path("/primary_address/{tn}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    public Address getPrimaryAddress(@PathParam("tn") String tn) throws VendorException, InvalidTNException;
    
    /**
     * {@inheritDoc}
     * 
     * 
     * <br/>
     * Notice that this service will return a list of addresses, the output will show the subtle differences.
     * 
     * <pre>
     * 
     * <b>Json</b>
     * <code>
     * curl -H "Accept: application/json" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/previous_addresses/1234567890
     * 
     * [{"houseNumber":"123","street":"Test Street 1","thoroughfare":null,"unitNumber":null,"preDirection":null,"postDirection":null,"city":"Houston","state":"TX","zip":"75042","unit":null,"country":null}]
     * </code>
     * 
     * <b>XML</b>
     * {@code
     * curl -H "Accept: application/xml" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/previous_addresses/1234567890
     * 
     * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<addresses>
     * 		<address>
     * 			<city>Houston</city>
     * 			<houseNumber>123</houseNumber>
     * 			<state>TX</state>
     * 			<street>Test Street 1</street>
     * 			<zip>75042</zip>
     * 		</address>
     * 	</addresses>
     * }
     * </pre>
     */
    @GET
    @Path("/previous_addresses/{tn}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    public List<Address> getPreviousAddresses(@PathParam("tn") String tn) throws InvalidTNException;
    
    /**
     * {@inheritDoc}
     * 
     * 
     * 
     * Notice that this service will return a list of addresses, the output will show the subtle differences.
     * 
     * <pre>
     * 
     * <b>Json</b>
     * <code>
     * curl -H "Accept: application/json" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/pending_addresses/1234567890
     * 
     * [{"houseNumber":"123","street":"Test Street 1","thoroughfare":null,"unitNumber":null,"preDirection":null,"postDirection":null,"city":"Houston","state":"TX","zip":"75042","unit":null,"country":null}]
     * </code>
     * 
     * <b>XML</b>
     * {@code
     * curl -H "Accept: application/xml" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/pending_addresses/1234567890
     * 
     * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<addresses>
     * 		<address>
     * 			<city>Houston</city>
     * 			<houseNumber>123</houseNumber>
     * 			<state>TX</state>
     * 			<street>Test Street 1</street>
     * 			<zip>75042</zip>
     * 		</address>
     * 	</addresses>
     * }
     * </pre>
     */
    @GET
    @Path("/pending_addresses/{tn}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Override
    public List<Address> getPendingAddressChanges(@PathParam("tn") String tn) throws InvalidTNException;
    
    
    /**
     * {@inheritDoc}
     * 
     * 
     * Notice that this service will return a list of addresses, the output will show the subtle differences.
     * 
     * <pre>
     * 
     * <b>Json</b>
     * <code>
     * curl -X POST -H "Content-Type: application/json"  -H "Accept: application/json" -d '{"houseNumber":"123","street":"Test Street 1","thoroughfare":null,"unitNumber":null,"preDirection":null,"postDirection":null,"city":"Houston","state":"TX","zip":"75042","unit":null,"country":null}' http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/validate/1234567895
     * 
     * {"status":0,"errorMessage":null,"hints":[{"houseNumber":"123","street":"Test Street 1","thoroughfare":null,"unitNumber":null,"preDirection":null,"postDirection":null,"city":"Houston","state":"TX","zip":"75042","unit":null,"country":null}],"valid":true}
     * </code>
     * 
     * <b>XML</b>
     * {@code
     * curl -X POST -H "Content-Type: application/xml"  -H "Accept: application/xml" -d '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><address><city>Houston</city><houseNumber>123</houseNumber><state>TX</state><street>Test Street 1</street><zip>75042</zip></address>' http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/validate/1234567890
     * 
     * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	 *		<addressValidationResponse>
	 *			<status>0</status>
	 *			<hints>
	 *				<city>Houston</city>
	 *				<houseNumber>123</houseNumber>
	 *				<state>TX</state>
	 *				<street>Test Street 1</street>
	 *				<zip>75042</zip>
	 *			</hints>
	 *			<valid>true</valid>
	 *		</addressValidationResponse>
     * 
     * }
     * </pre>
     */
    @POST
    @Path("/validate/{tn}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Override
    public AddressValidationResponse validateAddress(@PathParam("tn") String tn, Address address) throws InvalidTNException;

    
    /**
     * {@inheritDoc}
     * 
     * 
     * Notice that this service will return a list of addresses, the output will show the subtle differences.
     * 
     * <pre>
     * <b>Examples</b>
     * 
     * <b>Json</b>
     * <code>
     * curl -X POST -H "Content-Type: application/json"  -H "Accept: application/json" -d '{"houseNumber":"123","street":"Test Street 1","thoroughfare":null,"unitNumber":null,"preDirection":null,"postDirection":null,"city":"Houston","state":"TX","zip":"75042","unit":null,"country":null}' http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/validate/1234567895
     * 
     * {"status":0,"errorMessage":null,"hints":[{"houseNumber":"123","street":"Test Street 1","thoroughfare":null,"unitNumber":null,"preDirection":null,"postDirection":null,"city":"Houston","state":"TX","zip":"75042","unit":null,"country":null}],"valid":true}
     * </code>
     * 
     * <b>XML</b>
     * 
     * {@code
     * curl -X POST -H "Content-Type: application/xml"  -H "Accept: application/xml" -d '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><addressChangeRequest><tn>1234567890</tn><address><city>Houston</city><houseNumber>123</houseNumber><state>TX</state><street>Test Street 1</street><zip>75042</zip></address></addressChangeRequest>' http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/changeRequest
     * 
	 *	<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
	 *  	<response>
	 *  	<status>0</status>
	 *  </response>
     * }
     * </pre>
     */
    @POST
    @Path("/changeRequest")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Override
    public Response changeAddressRequest(AddressChangeRequest request) throws VendorException, InvalidTNException;

    /**
     * {@inheritDoc}
     * 
     * <pre>
     * <b>JSON</b>
     * <code>
     * curl -X POST -H "Content-Type: application/json" -H "Accept: application/json" -d '{"houseNumber":"123","street":"Test Street 1","thoroughfare":null,"unitNumber":null,"preDirection":null,"postDirection":null,"city":"Houston","state":"TX","zip":"75042","unit":null,"country":null}' http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/change/1234567890 
     * 
     * {"status":0,"errorMessage":null}
     * </code>
     * 
     * <b>XML</b>
     * {@code
     * curl -X POST -H "Content-Type: application/xml"  -H "Accept: application/xml" -d '<?xml version="1.0" encoding="UTF-8" standalone="yes"?><address><city>Houston</city><houseNumber>123</houseNumber><state>TX</state><street>Test Street 1</street><zip>75042</zip></address>' http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/change/1234567890
     * 
     * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<response>
     * 	<status>0</status>
     *  </response>
     * }
     * </pre>
     */
    @POST
    @Path("/change/{tn}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Override
    public Response changeAddress(@PathParam("tn") String tn, Address address) throws VendorException, InvalidTNException;
    
    /**
     * This is a helper if you want to use an html form to do the address change submission. This
     * is delegated to the {@link #changeAddressRequest(AddressChangeRequest)} after being converted
     * to an {@link AddressChangeRequest} object.
     */
    @POST
    @Path("/changeform")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    public Response changeAddressForm(
            @FormParam("tn") String tn, 
            @FormParam("streetnumber") String streetNumber,
            @FormParam("streetname") String streetName,
            @FormParam("predirection") String predirection,
            @FormParam("streettype") String streetType,
            @FormParam("city") String city,
            @FormParam("zip") String zip,
            @FormParam("unit") String unit,
            @FormParam("state") String state,
            @FormParam("country") String country, 
            @FormParam("reset") String reset, 
            @FormParam("acknowledge") String acknowledge) throws VendorException, InvalidTNException;

    /**
     * Change a collection of addresses.
     *
     * @param requests Collection of address change requests based on phone
     * number and address
     * @throws VendorException 
     */
    @POST
    @Path("/change_addresses")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    public CompositeResponse<Response> changeAddresses(AddressChangeRequests requests) throws VendorException;

    /**
     * {@inheritDoc}
     * 
     * <pre>
     * <b>JSON</b>
     * <code>
     * curl -X POST -H "Accept: application/json"  http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/reset/1234567890
     *
     * {"status":0,"errorMessage":null,"currentAddress":{"houseNumber":"123","street":"Test Street 1","thoroughfare":null,"unitNumber":null,"preDirection":null,"postDirection":null,"city":"Houston","state":"TX","zip":"75042","unit":null,"country":null}}
     * </code>
     * 
     * 
     *{@code
     * curl -X POST -H "Accept: application/json"  http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/reset/1234567890
     * 
     * <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
     * 	<addressResetResponse>
     * 		<status>0</status>
     * 		<currentAddress>
     * 			<city>Houston</city>
     * 			<houseNumber>123</houseNumber>
     * 			<state>TX</state>
     * 			<street>Test Street 1</street>
     * 			<zip>75042</zip>
     * 		</currentAddress>
     * 	</addressResetResponse>
     * }
     * 
     * </pre>
     */
    @POST
    @Path("/reset/{tn}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    public AddressResetResponse resetAddress(@PathParam("tn") String tn) throws InvalidTNException;

    /**
     * This is exposed to reset a list of address at once. This is an internal method used for
     * other systems to have the ability to reset a bulk number of addresses. This should not
     * be called from an external client.
     */
    @POST
    @Path("/reset_addresses")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    public CompositeResponse<AddressResetResponse> resetAddresses(AddressResetRequests requests);

    /**
     * {@inheritDoc}
     */
    @POST
    @Path("/cancel/{tn}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    public Response cancelAddressChange(@PathParam("tn") String tn) throws InvalidTNException;
    
    /**
     * 
     * <pre>
     * List of US based states, as of right now we don't support anything outside the US. I think
     * this will be the rule considering it is US base 911 system. At some point we may need
     * to support other countriy's emergency services but that is for another day.
     * 
     * <b>JSON</b>
     * {@code
     * curl -H "Accept: application/json" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/states
     * }
     * 
     * 
     * <b>XML</b>
     * {@code
     * curl -H "Accept: application/xml" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/states
     * }
     * 
     * </pre>
     * 
     * @return
     */
    @GET
    @Path("/states")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
    public List<State> listStates();

    /**
     * {@inheritDoc}
     * 
     * <pre>
     * 
     * This will return helper list of Thoroughfares for the US. These are from the US Postal Service as their
     * supported list of Thoroughfares.
     * 
     * <b>JSON</b>
     * {@code
     * curl -H "Accept: application/json" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/thoroughfares
     * }
     * 
     * 
     * <b>XML</b>
     * {@code
     * curl -H "Accept: application/xml" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/thoroughfares
     * }
     * 
     * Unformatted list of ouptuts, these will be formatted in the media type that was requested but due 
     * to the leght they are shown here as constants.
     * 
     * <code>
     * ADDN("Addn"),
	 * AFB("Afb"),
	 * ALY("Alley"),
	 * ANX("Annex"),
	 * APT("Apartment"),
	 * APTS("Apts"),
	 * ARC("Arcade"),
	 * ARCH("Arch"),
	 * ARPT("Arpt"),
	 * AV("Av"),
	 * AVE("Avenue"),
	 * BASE("Base"),
	 * BAY("Bay"),
	 * ....
	 * </code>
	 * 
	 * </pre>
     * @return an enumerated list of {@link Thoroughfare} 
     */
    @GET
    @Path("/thoroughfares")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
	public List<Thoroughfare> listThoroughfares();
    
    /**
     * {@inheritDoc}
     * 
     * <pre>
     * 
     * This will list all the options available for directional. This is for use in building
     * a front end view in order to show all the available directional attributes.
     * 
     * 
     * <b>JSON</b>
     * {@code
     * curl -H "Accept: application/json" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/directional
     * }
     * 
     * <b>XML</b>
     * {@code
     * curl -H "Accept: application/xml" http://tweety.adpt-tech.net:8080/e911-example/rest/v1/address/directional
     * }
     * 
     * Unformatted list of ouptuts, these will be formatted in the media type that was requested but due 
     * to the leght they are shown here as constants.
     * <code>
     * 	N("North"),
	 *	S("South"),
	 *	E("East"),
	 *	W("West"),
	 *	NE("Northeast"),
	 *	NW("Northwest"),
	 *	SE("SouthEast"),
	 *	SW("SouthWest");
	 *</code>
	 *
	 *</pre>
     * 
     * @return an enumerated list of {@link Directional} objects
     */
    @GET
    @Path("/directional")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
	public List<Directional> listDirectional();
}