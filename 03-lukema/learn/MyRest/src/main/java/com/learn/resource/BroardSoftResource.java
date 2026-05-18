package com.learn.resource;


import java.io.Serializable;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.element.TodoResponse;
import com.learn.element.bsft.PsRequest;


@Path("v1")
public class BroardSoftResource
    implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    @POST
    @Path("distributors/postman")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public TodoResponse doPost(@Valid PsRequest psRequest) {
        LOG.info("doPost");

        LOG.info("Input: " + psRequest.toString());

        TodoResponse todoStatus = new TodoResponse();
        todoStatus.setDescription("BroardSoftResource Description");
        todoStatus.setStatus(Status.OK);

        //return Response.status(Status.OK).entity(todoStatus).build(); 
        return todoStatus;
    }

}
