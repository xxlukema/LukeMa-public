package com.learn.rest.resource;


import java.io.Serializable;
import java.util.List;

import javax.interceptor.Interceptors;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.learn.bean.House;
import com.learn.dao.HouseDao;
import com.learn.interceptor.MyInterceptor;


@Path("house")
public class HouseResource
    implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    /**
     * curl -k -i -X GET http://localhost:8080/MyProperties/rest/house/getall
     */
    @GET
    @Path("getDateUpdated")
    @Produces(MediaType.TEXT_PLAIN)
    @Interceptors(MyInterceptor.class)
    public String getDateUpdated() {
        LOG.debug("Enter.");

        return HouseDao.DATE_UPDATED;
    }
    
    /**
     * curl -k -i -X GET http://localhost:8080/MyProperties/rest/house/getall
     */
    @GET
    @Path("getPropertyList")
    @Produces(MediaType.APPLICATION_JSON)
    @Interceptors(MyInterceptor.class)
    public List<House> getAllProperties() {
        LOG.debug("Enter.");

        return HouseDao.getAllHouseList();
    }

}
