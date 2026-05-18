package com.learn.rest.resource;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.learn.rest.element.RentProperty;
import com.learn.rest.element.Todo;

import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/rent")
@Service()
public class RentResource {
    private static final Logger log = LogManager.getLogger();

    // @Autowired
    // PortfolioUserService userService;

    // @Autowired
    // PortfolioItemService portfolioItemService;

    @POST
    @Path("/addRentProperty")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Date addRentProperty(@Valid RentProperty rentProperty) {
        log.debug(() -> "Enter addRentProperty...");

        log.debug("Input: {}", () -> rentProperty.toString());

        /** return Response.status(Status.OK).entity(todoStatus).build(); */

        log.debug(() -> "Leave addRentProperty.");

        return new Date();
    }

    @PUT
    @Path("foc")
    //@Produces({ MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
    //@Consumes({ MediaType.APPLICATION_XML, MediaType.TEXT_PLAIN })
    public String foc(String str) {

        log.info(str);

        if (str != null) {
            str = str.toUpperCase();
        }

        return str;
    }

    @GET
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Todo getAppXML() {
        log.debug(() -> "getAppXML");

        Todo todo = new Todo();
        todo.setId(1L);
        todo.setSummary("First summary");
        todo.setDescription("First description");
        return todo;
    }

    @GET
    @Produces({ MediaType.TEXT_XML })
    public Todo getHTML() {
        log.debug(() -> "getHTML");

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
        log.debug(() -> "getMoreHTML");

        log.debug("Input: {}", () -> todo.toString());

        List<Todo> list = new ArrayList<>();

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
