package com.learn.filter;


import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.MultivaluedMap;
import jakarta.ws.rs.ext.Provider;


@Provider
public class JerseyRequestResponseFilter
    implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger log = LogManager.getLogger();

    /*
    public ContainerRequest filter(ContainerRequest request) {

        if (request.getMediaType() == null) {
            return request;
        }

        log.debug(() -> "#### Intercepted MediaType ####\n" + request.getMediaType().toString());

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        InputStream in = request.getEntityInputStream();
        final StringBuilder sb = new StringBuilder();
        try {
            if (in.available() > 0) {
                ReaderWriter.writeTo(in, out);

                byte[] requestEntity = out.toByteArray();

                sb.append(new String(requestEntity)).append("\n");

                request.setEntityInputStream(new ByteArrayInputStream(requestEntity));
            }
            log.debug(() -> "#### Intercepted Entity ####\n" + sb.toString());
            return request;
        } catch (IOException ex) {
            throw new ContainerException(ex);
        }
    }
    */

    @Override
    public void filter(ContainerRequestContext context)
        throws IOException {
        log.debug(() -> "Intercepted Request URI: " + context.getUriInfo().getRequestUri());
        log.debug(() -> "Intercepted Request MediaType: " + context.getMediaType());
        log.debug(() -> "Intercepted Request Headers: " + context.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
        throws IOException {

        log.debug(() -> "Intercepted Request URI: " + requestContext.getUriInfo().getRequestUri());
        log.debug(() -> "Intercepted Request MediaType: " + requestContext.getMediaType());
        log.debug(() -> "Intercepted Request Headers: " + requestContext.getHeaders());

        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        headers.add("Access-Control-Allow-Origin", "*");
        //headers.add("Access-Control-Allow-Origin", "http://podcastpedia.org"); //allows CORS requests only coming from podcastpedia.org
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");

        log.debug(() -> "Intercepted Response MediaType: " + responseContext.getMediaType());
        log.debug(() -> "Intercepted Response Headers: " + responseContext.getHeaders());
        log.debug(() -> "Intercepted Response Status: " + responseContext.getStatus() + " " + responseContext.getStatusInfo());

    }
}
