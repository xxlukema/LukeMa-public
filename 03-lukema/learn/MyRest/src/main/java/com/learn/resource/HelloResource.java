package com.learn.resource;

import java.io.Serializable;
import java.net.URI;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.interceptor.MyInterceptor;

@Path("hello")
public class HelloResource implements Serializable {

	private static final long serialVersionUID = 1L;

	private static final Logger LOG = LogManager.getLogger();

	/*
	 * static { DOMConfigurator.configureAndWatch("/Users/lma/tmp/log4j.xml",
	 * 5000); }
	 */

	@Context
	private UriInfo uriInfo;

	// @Inject
	// private MyNamedBean myNamedBean;

	private int counter = 0;

	@PostConstruct
	public void postConstruct() {
		LOG.debug("counter = " + ++counter);
	}

	@PreDestroy
	public void preDestroy() {
		LOG.debug("counter = " + ++counter);
	}

	@GET
	@Path("0/{username}")
	@Produces(MediaType.TEXT_PLAIN)
	@Interceptors(MyInterceptor.class)
	public String sayPlainTextHello(@PathParam("username") String userName) {
		LOG.debug("Enter.");
		printUriInfo();
		return "Hello Jersey: " + userName + " counter = " + ++counter;
	}

	/**
	 * curl -k -i -H "MyHeaderParam: Luke\r\n Header%0D%0A Param" -H "Line: Line
	 * One;\r\nLine Two: Another%0D%0A Line" -X GET
	 * http://localhost:8080/MyRest/rest/hello/1/Hong%20Lin
	 */
	@GET
	@Path("1/{username}")
	@Produces(MediaType.TEXT_XML)
	@Interceptors(MyInterceptor.class)
	public Response sayXMLHello(@Context HttpHeaders headers, @HeaderParam("MyHeaderParam") String myHeaderParam,
			@HeaderParam("Referer") String referer, @PathParam("username") String userName) {
		LOG.debug("Enter.");
		printUriInfo();

		LOG.info("MyHeaderParam: " + myHeaderParam);
		LOG.info("Referer: " + referer);

		for (String key : headers.getRequestHeaders().keySet()) {
			LOG.info(key + ": \t" + headers.getRequestHeaders().get(key));
		}

		String result = "<?xml version=\"1.0\"?>" + "<hello> Hello Jersey: " + userName + "</hello>";
		return Response.status(Status.OK).entity(result).build();
	}

	@GET
	@Path("2/{username}")
	@Produces(MediaType.TEXT_HTML)
	@Interceptors(MyInterceptor.class)
	public String sayHtmlHello(@PathParam("username") String userName) {
		LOG.debug("Enter.");
		printUriInfo();
		return "<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" + "Hello Jersey: " + userName
				+ "</body></h1>" + "</html> ";
	}

	@GET
	@Path("3/{username}")
	@Produces(MediaType.TEXT_HTML)
	@Interceptors(MyInterceptor.class)
	public String sayHtmlHello2(@PathParam("username") String userName) {
		LOG.debug("Enter.");
		printUriInfo();
		return "<html> " + "<title>" + "Hello Jersey" + "</title>" + "<body><h1>" + "Hello Jersey: " + userName
				+ "</body></h1>" + "</html> ";
	}

	private void printUriInfo() {
		URI uri = uriInfo.getAbsolutePath();
		LOG.debug("URI: " + uri);
		LOG.debug("counter = " + ++counter);

		LOG.debug("1111111111 debug 111111111111");
		LOG.info("2222222222 info 2222222222");

		// myNamedBean.sayHello();

	}

}
