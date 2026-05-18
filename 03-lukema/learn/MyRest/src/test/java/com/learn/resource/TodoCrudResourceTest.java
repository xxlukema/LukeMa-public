package com.learn.resource;


import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.learn.element.Todo;
import com.learn.element.TodoResponse;
import com.learn.utl.UrlConstants;


public class TodoCrudResourceTest {

    private static final Logger LOG = LogManager.getLogger();

    private WebTarget todoCrud = null;

    private static URI getBaseURI() {
        return buildURI(UrlConstants.URL);
    }

    private static URI buildURI(String url) {
        return UriBuilder.fromUri(url).build();
    }

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");

        ClientConfig config = new ClientConfig();
        Client client = ClientBuilder.newClient(config);
        WebTarget target = client.target(getBaseURI());

        WebTarget rest = target.path("rest");
        todoCrud = rest.path("todoCrud");
    }

    @Test
    public void testGet() {
        LOG.info(todoCrud.request().accept(MediaType.TEXT_XML).get(String.class));

        LOG.info(todoCrud.request().accept(MediaType.APPLICATION_JSON).get(String.class));

        String xml = todoCrud.request().accept(MediaType.APPLICATION_XML).get(String.class);
        LOG.info("xml = " + xml);

        List<Todo> todos = todoCrud.request(MediaType.APPLICATION_XML).get(new GenericType<List<Todo>>() {
        });
        LOG.info("todos.size() = " + todos.size());
        for (Todo todo : todos) {
            LOG.info(todo.toString());
        }
    }

    @Test
    public void testPut() {

        Todo input = new Todo();

        input.setId(222L);
        input.setDescription("DDD");
        input.setSummary("SSS");

        TodoResponse response = todoCrud.request(MediaType.APPLICATION_JSON).put(Entity.entity(input, MediaType.APPLICATION_JSON), TodoResponse.class);
        LOG.info(response);

        Assert.assertEquals("PUT", Status.OK, response.getStatus());
    }

    @Test
    public void testDelete() {

        TodoResponse response = todoCrud.path("222").request().delete(TodoResponse.class);
        LOG.info(response);

        Assert.assertEquals("DELETE", Status.OK, response.getStatus());
    }

    @Test
    public void testPostForm() {

        Form form = new Form();
        form.param("id", "101");
        form.param("summary", "Summary 1111111111");
        form.param("description", "Description 111111111");

        TodoResponse response = todoCrud.path("formPost").request().accept(MediaType.APPLICATION_XML).post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED),
                TodoResponse.class);

        LOG.info(response);

        Assert.assertEquals("POST", Status.OK, response.getStatus());
    }

    @Test
    public void testPostJaxb() {
        Todo input = new Todo();
        input.setId(102L);
        input.setDescription("DDD 2");
        input.setSummary("SSSS 2");

        ClientResponse response = todoCrud.request().accept(MediaType.APPLICATION_XML).post(Entity.entity(input, MediaType.APPLICATION_XML), ClientResponse.class);

        LOG.info("Form response: " + response.getStatus());
        LOG.info("Form response: " + response.readEntity(String.class));

        Assert.assertEquals("POST", 200, response.getStatus());
    }

    @Test
    public void testMorePut() {

        Todo input = new Todo();

        input.setId(222L);
        input.setDescription("Desc");
        input.setSummary("Summary");

        WebTarget more = todoCrud.path("more");

        GenericType<List<Todo>> response = new GenericType<List<Todo>>() {
        };

        List<Todo> list = more.request().accept(MediaType.APPLICATION_XML).put(Entity.entity(input, MediaType.APPLICATION_XML), response);

        LOG.info("list.size() = " + list.size());
        for (Todo elem : list) {
            LOG.info("More: " + elem.toJSon());
        }
    }
}
