package com.learn.rest.resource;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.rest.element.RentProperty;
import com.learn.rest.element.Todo;
import com.learn.service.PortfolioItemService;
import com.learn.service.PortfolioUserService;


@Path("/rent")
@Service()
public class RentResource {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    PortfolioUserService userService;

    @Autowired
    PortfolioItemService portfolioItemService;

    @POST
    @Path("/addRentProperty")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Date addRentProperty(@Valid RentProperty rentProperty) {
        LOG.debug("Enter addRentProperty...");

        LOG.debug("Input: " + rentProperty.toString());

        //return Response.status(Status.OK).entity(todoStatus).build(); 

        LOG.info("Leave addRentProperty.");

        return new Date();
    }

    @PUT
    @Path("foc")
    //@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
    //@Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
    public String foc(String str) {

        LOG.info(str);

        if (str != null) {
            str = str.toUpperCase();
        }

        return str;
    }

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Todo getAppXML() {
        LOG.info("getAppXML");

        Todo todo = new Todo();
        todo.setId(1L);
        todo.setSummary("First summary");
        todo.setDescription("First description");
        return todo;
    }

    @GET
    @Produces({ MediaType.TEXT_XML })
    public Todo getHTML() {
        LOG.info("getHTML");

        Todo todo = new Todo();
        todo.setId(2L);
        todo.setSummary("Second summary");
        todo.setDescription("Second description");
        return todo;
    }

    @PUT
    @Path("more")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Todo> getMoreHTML(@Valid Todo todo) {
        LOG.info("getMoreHTML");

        LOG.info("Input: " + todo.toString());

        List<Todo> list = new ArrayList<Todo>();

        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setSummary("1st summary");
        todo1.setDescription("1st description");
        list.add(todo1);

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setSummary("2nd summary");
        todo2.setDescription("2nd description");
        list.add(todo2);

        list.add(todo);

        return list;
    }
}
