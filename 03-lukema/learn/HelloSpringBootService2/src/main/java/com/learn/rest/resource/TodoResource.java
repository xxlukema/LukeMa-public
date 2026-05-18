package com.learn.rest.resource;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.rest.element.Todo;
import com.learn.rest.element.TodoResponse;


@Path("todo")
public class TodoResource {
    private static final Logger LOG = LogManager.getLogger();

    @POST
    @Path("validateParam")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    //@Produces({ MediaType.APPLICATION_JSON })
    //@Consumes({ MediaType.APPLICATION_JSON })
    public TodoResponse doPost(@Valid Todo todo) {
        LOG.info("doPost");

        LOG.info("Input: " + todo.toString());

        TodoResponse todoStatus = new TodoResponse();
        todoStatus.setDescription(todo.getDescription());
        todoStatus.setStatus(Status.OK);

        //return Response.status(Status.OK).entity(todoStatus).build(); 
        return todoStatus;
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
