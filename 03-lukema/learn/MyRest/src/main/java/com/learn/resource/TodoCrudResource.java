package com.learn.resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.dao.TodoCrudDao;
import com.learn.element.Todo;
import com.learn.element.TodoResponse;

/**
 * @formatter:off
 * 
 */
@Path("todoCrud")
public class TodoCrudResource {
	private static final Logger LOG = LogManager.getLogger();

	@Context
	UriInfo uriInfo;
	@Context
	ServletContext context;
	@Context
	SecurityContext securityContext;

	// Return the list of todos to the user in the browser
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Todo> getTodosForBrowser() {
		LOG.debug("getTodosForBrowser");

		return TodoCrudDao.getInstance().list();
	}

	// Return the list of todos for applications
	@GET
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public List<Todo> getTodoList() {
		LOG.debug("getTodoList");

		return TodoCrudDao.getInstance().list();
	}

	@GET
	@Path("callJSon/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Todo getJSon(@PathParam("id") long id, @Context Request request) {
		LOG.debug("getJSon: context = " + context);
		LOG.debug("getJSon: uriInfo = " + uriInfo);
		LOG.debug("getJSon: request = " + request);
		LOG.debug("getJSon: securityContext = " + securityContext);

		Todo todo = TodoCrudDao.getInstance().get(id);

		return todo;
	}

	/**
	 * PUT creates a new resource, must also be idempotent.
	 */
	@PUT
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	public TodoResponse doPut(JAXBElement<Todo> todo, @Context HttpServletResponse servletResponse) throws IOException {
		LOG.debug("doPut");

		Todo input = todo.getValue();

		TodoResponse todoStatus = new TodoResponse();

		Todo dbTodo = TodoCrudDao.getInstance().update(input);

		if (dbTodo == null) {
			todoStatus.setStatus(Status.BAD_REQUEST);
			todoStatus.setDescription("Entity no found");
		} else {
			todoStatus.setStatus(Status.OK);
			todoStatus.setDescription("OK Desc");
		}

		// servletResponse.sendRedirect("TodoCrud.htm");

		return todoStatus;
	}

	/**
	 * DELETE removes the resources. The operations are idempotent, they can get
	 * repeated without leading to different results.
	 */
	@DELETE
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Path("{id}")
	public TodoResponse doDelete(@PathParam("id") Long id, @Context HttpServletResponse servletResponse)
			throws IOException {
		LOG.debug("doDelete");

		TodoResponse todoStatus = new TodoResponse();

		Todo dbTodo = TodoCrudDao.getInstance().delete(id);

		if (dbTodo == null) {
			todoStatus.setStatus(Status.BAD_REQUEST);
			todoStatus.setDescription("Entity no found");
		} else {
			todoStatus.setStatus(Status.OK);
			todoStatus.setDescription("OK Desc");
		}

		// servletResponse.sendRedirect("../TodoCrud.htm");

		return todoStatus;
	}

	/**
	 * @FormParam only accepts application/x-www-form-urlencoded
	 * 
	 *            curl -k -i -H "Content-Type:
	 *            application/x-www-form-urlencoded" -X POST -F 'id=My Id' -F
	 *            "summary=sum" -F description="My Desc"
	 *            http://localhost:8080/MyRest/rest/todoCrud/formPost
	 * 
	 */
	@POST
	@Path("formPost")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
	public TodoResponse doPostForm(@FormParam("id") Long id, @FormParam("summary") String summary,
			@FormParam("description") String description, @Context HttpServletResponse servletResponse)
			throws IOException {
		LOG.debug("doPostForm");

		LOG.info(id);
		LOG.info(summary);
		LOG.info(description);

		Todo todo = new Todo();
		todo.setId(id);
		todo.setDescription(description);
		todo.setSummary(summary);

		TodoResponse todoStatus = new TodoResponse();

		TodoCrudDao.getInstance().update(todo);

		todoStatus.setStatus(Status.OK);
		todoStatus.setDescription("OK Desc");

		// servletResponse.sendRedirect("../../TodoCrud.htm");

		return todoStatus;
	}

	/**
	 * 
	 * 
	 */
	@POST
	@Path("xmlPost")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_XML })
	public TodoResponse doPostXml(@Context HttpHeaders headers, @HeaderParam("MyHeaderParam") String myHeaderParam,
			@HeaderParam("Referer") String referer, JAXBElement<Todo> todo) throws IOException {
		LOG.debug("doPostXml");

		LOG.info("MyHeaderParam: " + myHeaderParam);
		LOG.info("Referer: " + referer);

		for (String key : headers.getRequestHeaders().keySet()) {
			LOG.info(key + ": \t" + headers.getRequestHeaders().get(key));
		}

		Todo input = todo.getValue();

		LOG.info(input);
		LOG.info(input.getId());
		LOG.info(input.getSummary());
		LOG.info(input.getDescription());

		TodoResponse todoStatus = new TodoResponse();

		TodoCrudDao.getInstance().update(input);

		todoStatus.setStatus(Status.OK);
		todoStatus.setDescription("OK Desc");

		// servletResponse.sendRedirect("../../TodoCrud.htm");

		return todoStatus;
	}

	/**
	 * 
	 * 
	 */
	@POST
	@Path("jsonPost")
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Consumes({ MediaType.APPLICATION_JSON })
	public TodoResponse doPostJson(Todo todo) throws IOException {
		LOG.debug("doPostJson");

		LOG.info(todo);
		LOG.info(todo.getId());
		LOG.info(todo.getSummary());
		LOG.info(todo.getDescription());

		TodoResponse todoStatus = new TodoResponse();

		TodoCrudDao.getInstance().update(todo);

		todoStatus.setStatus(Status.OK);
		todoStatus.setDescription("OK Desc");

		// servletResponse.sendRedirect("../../TodoCrud.htm");

		return todoStatus;
	}

	/**
	 * POST updates an existing resource or creates a new resource.
	 */
	@POST
	@Produces(MediaType.APPLICATION_XML)
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED })
	public void doPostJaxb(JAXBElement<Todo> todo, @Context HttpServletResponse servletResponse) throws IOException {
		LOG.debug("doPostJaxb");

		Todo input = todo.getValue();

		TodoCrudDao.getInstance().update(input);

		servletResponse.sendRedirect("../TodoCrud.htm");
	}

	@PUT
	@Path("more")
	@Produces({ MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_XML })
	public List<Todo> morePut(JAXBElement<Todo> todo) {
		LOG.info("morePut");

		Todo input = todo.getValue();

		LOG.info("Input: " + input.toString());

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

		list.add(input);

		return list;
	}
}
