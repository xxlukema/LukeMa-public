package com.broadsoft.cpbx.e911.servlet;


import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.broadsoft.cpbx.e911.api.IOCE911Binder;


/**
 * Servlet 3.0 register the rest service. This uses the 
 * package configuration to discover resources in the 
 * "com.broadsoft.cpbx.e911.rest.resources" package.
 * 
 * @author chris
 *
 */
@ApplicationPath("/rest/v1/")
public class RestApplication
    extends ResourceConfig {

    public RestApplication() {
        packages("com.broadsoft.cpbx.e911.resource");
        // register our DI module
        register(new IOCE911Binder());
    }
}
