package com.learn.resource;


import java.net.URI;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.learn.element.Todo;
import com.learn.utl.UrlConstants;


public class TodoResourceTest {

    private static final Logger LOG = LogManager.getLogger();

    private WebTarget webTarget;

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

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(getBaseURI());

        WebTarget rest = target.path("rest");

        webTarget = rest.path("todo");
    }

    //@Ignore
    @Test
    public void testPost() {

        WebTarget validateParam = webTarget.path("validateParam");

        Todo input = new Todo();

        input.setId(222L);
        input.setDescription("a"); // site id
        //input.setSummary("TX1"); // State

        Response response = validateParam.request().accept(MediaType.APPLICATION_XML).post(Entity.entity(input, MediaType.APPLICATION_XML), Response.class);

        LOG.info(response.getStatus());
        LOG.info(response.getStatusInfo());
        LOG.info(response.getMediaType());
        LOG.info(response.toString());

        //Assert.assertEquals("POST", 200, response.getStatusInfo().getStatusCode());

        if (response.getStatusInfo().getStatusCode() == 500) {
            //ValidationError validationError = response.readEntity(ValidationError.class);

            //ConstraintViolationException constraintViolationException = response.readEntity(ConstraintViolationException.class);

            //Set<ValidationError> errors = constraintViolationException.getConstraintViolations();

            //validationError.
        }

        //TodoStatus todoStatus = response.readEntity(TodoStatus.class);
        //LOG.info(todoStatus);

        String str = response.readEntity(String.class);
        LOG.info(str);

    }

    @Ignore
    @Test
    public void testPutFoc() {

        WebTarget validateParam = webTarget.path("foc");

        Response response = validateParam.request().accept(MediaType.APPLICATION_XML).put(Entity.entity("Hello Foc.", MediaType.TEXT_PLAIN), Response.class);

        LOG.info(response.getStatus());
        LOG.info(response.getStatusInfo());
        LOG.info(response.getMediaType());
        LOG.info(response.toString());

        //Assert.assertEquals("POST", 200, response.getStatusInfo().getStatusCode());

        //TodoStatus todoStatus = response.readEntity(TodoStatus.class);
        //LOG.info(todoStatus);

        String str = response.readEntity(String.class);
        LOG.info(str);

    }

    @Ignore
    @Test
    public void runTest() {

        LOG.info("1 JSON: " + webTarget.request().accept(MediaType.APPLICATION_JSON).get(String.class));

        LOG.info("2 XML: " + webTarget.request().accept(MediaType.APPLICATION_XML).get(String.class));

        LOG.info("3 : " + webTarget.request().accept(MediaType.TEXT_XML).get(String.class));

    }

    @Ignore
    @Test
    public void testPutValidParam() {

        Todo input = new Todo();

        input.setId(222L);
        input.setDescription("Desc");
        input.setSummary("Summary");

        WebTarget more = webTarget.path("more");

        GenericType<List<Todo>> response = new GenericType<List<Todo>>() {
        };

        List<Todo> list = more.request().accept(MediaType.APPLICATION_JSON).put(Entity.entity(input, MediaType.APPLICATION_JSON), response);

        LOG.info("list.size() = " + list.size());

        Assert.assertEquals("list.size()", 3, list.size());

        for (Todo elem : list) {
            LOG.info("More: " + elem.toJSon());
        }
    }

    @Ignore
    @Test
    public void testPutNotValidParam() {

        Todo input = new Todo();

        input.setId(222L);
        //input.setDescription("Desc");
        //input.setSummary("Summary");

        WebTarget more = webTarget.path("more");

        GenericType<List<Todo>> response = new GenericType<List<Todo>>() {
        };

        List<Todo> list = more.request().accept(MediaType.APPLICATION_JSON).put(Entity.entity(input, MediaType.APPLICATION_JSON), response);

        LOG.info("list.size() = " + list.size());

        Assert.assertEquals("list.size()", 3, list.size());

        for (Todo elem : list) {
            LOG.info("More: " + elem.toJSon());
        }
    }
}
