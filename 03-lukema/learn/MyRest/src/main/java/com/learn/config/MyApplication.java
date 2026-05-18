package com.learn.config;


import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

import com.learn.exception.mapper.MyConstraintViolationExceptionMapper;


@ApplicationPath("rest")
public class MyApplication
    extends ResourceConfig {

    public MyApplication() {
        packages("com.learn.resource");

        register(org.glassfish.jersey.server.filter.UriConnegFilter.class);
        register(org.glassfish.jersey.server.validation.ValidationFeature.class);

        register(MyConstraintViolationExceptionMapper.class);

        property(ServerProperties.METAINF_SERVICES_LOOKUP_DISABLE, true);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }

}
