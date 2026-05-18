package com.broadsoft.cpbx.e911.resource;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.broadsoft.cpbx.e911.servlet.AddressOrderCompletionEvent;
import com.broadsoft.cpbx.e911.servlet.AddressPendingEvent;
import com.broadsoft.cpbx.e911.servlet.AddressPollingTimeoutEvent;
import com.google.common.eventbus.EventBus;

/**
 * Resource that allows you to test the event service that will
 * send events to btbc.
 * @author chris
 *
 */
@Path("/test/event")
public class TestEventResource {
	
	private final EventBus eventBus;
	
	@Inject
	public TestEventResource(EventBus eventBus) {
		this.eventBus = eventBus;
	}
	
	/**
	 * Test the completion event
	 * 
	 * <p>
	 * example using curl
	 * curl -X POST http://localhost:8080/e911-example/rest/v1/test/event/completion/1235
	 * </p>
	 * @param tn
	 */
	@POST
	@Path("completion/{tn}")
	public void testCompletion(@PathParam("tn") String tn) {
		eventBus.post(new AddressOrderCompletionEvent(tn));
	}
	
	/**
	 * Test a timeout event
	 * 
	 * <p>
	 * example using curl
	 * curl -X POST http://localhost:8080/e911-example/rest/v1/test/event/timeout/1235
	 * </p>
	 * @param tn
	 */
	@POST
	@Path("timeout/{tn}")
	public void testTimeout(@PathParam("tn") String tn) {
		eventBus.post(new AddressPollingTimeoutEvent(tn));
	}
	
	/**
	 * Test the address pending event.
	 * 
	 * <p>
	 * example using curl 
	 * curl -X POST http://localhost:8080/e911-example/rest/v1/test/event/pending/1235
	 * </p>
	 * @param tn
	 */
	@POST
	@Path("pending/{tn}")
	public void testPending(@PathParam("tn") String tn) {
		eventBus.post(new AddressPendingEvent(tn, 0, null));
	}
}
