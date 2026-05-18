package com.learn.rest.resource;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;
import javax.ws.rs.core.MediaType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.learn.exception.RestException;
import com.learn.rest.element.PropertyAddress;
import com.learn.rest.element.PropertySummary;


/**
 * Rest service for property lookup
 *
 * 1. ping:
 *
 *    curl -k -i -X GET https://localhost:8443/ping
 *
 * 2. Find property:
 *
 *    curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET https://localhost:8443/property?property_id=home_154
 *
 * 3. Exception: property_id cannot be 0
 *
 *    curl -k -i -H "Accept: application/json" -H "Content-Type: application/json" -X GET https://localhost:8443/property?property_id=home_0
 *
 * @author Luke Ma
 */
@RestController
public class PropertyLookupRestController {

    private static final Logger LOG = LogManager.getLogger();

    private final Map<String, PropertySummary> DB = new ConcurrentHashMap<>();

    @PostConstruct
    private void postConstruct() {
        PropertyAddress address = new PropertyAddress();
        address.setCity("New Ronaldborough");
        address.setState("VA");

        PropertySummary property = new PropertySummary();
        property.setAddress(address);
        property.setHomeId("home_154");
        property.setOwner("Stephanie Gould");
        property.setValue(305372F);

        DB.put(property.getHomeId(), property);
    }

    @GetMapping("/ping")
    public String ping() {

        LOG.info("Called. ping()");

        return "{\"status\":\"OK!\"}\n";
    }

    @GetMapping(value = "/property", consumes = { MediaType.APPLICATION_JSON }, produces = { MediaType.APPLICATION_JSON })
    public ResponseEntity<?> getProperty(@RequestParam(value = "property_id") String propertyId)
        throws RestException {

        LOG.info("Called. propertyId=" + propertyId);

        PropertySummary property = DB.get(propertyId);

        if ("0".equals(propertyId)) {
            throw new RestException("property_id cannot be 0");
        }

        if (property == null) {
            return new ResponseEntity<>("Property not found", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(property, HttpStatus.OK);
        }
    }

}
