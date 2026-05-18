package com.learn.filter;


import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Provider
public class JerseyFilter
    implements ContainerRequestFilter {
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
        LOG.info("Intercepted MediaType: " + context.getMediaType());
    }
}
