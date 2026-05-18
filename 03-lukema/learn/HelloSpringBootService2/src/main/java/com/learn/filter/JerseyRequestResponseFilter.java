package com.learn.filter;


import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Provider
public class JerseyRequestResponseFilter
    implements ContainerRequestFilter, ContainerResponseFilter {
    private static final Logger LOG = LogManager.getLogger();

    /*
    public ContainerRequest filter(ContainerRequest request) {
    
        if (request.getMediaType() == null) {
            return request;
        }
    
        LOG.info("#### Intercepted MediaType ####\n" + request.getMediaType().toString());
    
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
            LOG.info("#### Intercepted Entity ####\n" + sb.toString());
            return request;
        } catch (IOException ex) {
            throw new ContainerException(ex);
        }
    }
    */

    @Override
    public void filter(ContainerRequestContext context)
        throws IOException {
        LOG.info("Intercepted Request URI: " + context.getUriInfo().getRequestUri());
        LOG.info("Intercepted Request MediaType: " + context.getMediaType());
        LOG.info("Intercepted Request Headers: " + context.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
        throws IOException {

        LOG.info("Intercepted Request URI: " + requestContext.getUriInfo().getRequestUri());
        LOG.info("Intercepted Request MediaType: " + requestContext.getMediaType());
        LOG.info("Intercepted Request Headers: " + requestContext.getHeaders());

        MultivaluedMap<String, Object> headers = responseContext.getHeaders();

        headers.add("Access-Control-Allow-Origin", "*");
        //headers.add("Access-Control-Allow-Origin", "http://podcastpedia.org"); //allows CORS requests only coming from podcastpedia.org       
        headers.add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        headers.add("Access-Control-Allow-Headers", "X-Requested-With, Content-Type, X-Codingpedia");

        LOG.info("Intercepted Response MediaType: " + responseContext.getMediaType());
        LOG.info("Intercepted Response Headers: " + responseContext.getHeaders());
        LOG.info("Intercepted Response Status: " + responseContext.getStatus() + " " + responseContext.getStatusInfo());

    }
}
