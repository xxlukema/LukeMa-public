package com.broadsoft.cpbx.e911.resource;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {

	@Override
	public Response toResponse(Throwable exception) {
		exception.printStackTrace();
		return Response.serverError().entity(exception.getMessage()).build();
	}	
}