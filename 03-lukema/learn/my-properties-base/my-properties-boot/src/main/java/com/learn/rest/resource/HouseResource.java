package com.learn.rest.resource;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jakarta.ws.rs.core.MediaType;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.learn.dao.HouseDao;
import com.learn.pojo.House;

import lombok.extern.log4j.Log4j2;


@Log4j2
@RequestMapping("/rest/house")
@RestController
public class HouseResource
    implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * curl -k -i -X GET https://localhost:8443/rest/house/getDateUpdated
     * or
     * curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/house/getDateUpdated
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "getDateUpdated", produces = { MediaType.APPLICATION_JSON })
    public String getDateUpdated() {
        log.debug(() -> "Enter.");

        Date dateUpdated = new Date();
        String dateUpdatedStr = new SimpleDateFormat(HouseDao.DATE_FORMAT).format(dateUpdated);

        return String.format("{'dateUpdated':'%s'}", dateUpdatedStr).replace("'", "\"");
    }

    /**
     * curl -k -i -X GET https://localhost:8443/rest/house/getPropertyList
     * or
     * curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/house/getPropertyList
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "getPropertyList", produces = { MediaType.APPLICATION_JSON })
    public List<House> getAllProperties() {
        log.debug(() -> "Enter.");

        return HouseDao.getAllHouseList();
    }

    /**
     * curl -k -i -X GET https://localhost:8443/rest/house/getPropertyList
     * or
     * curl -k -i -X GET https://localhost:8443/my-properties-boot/rest/house/getPropertyList
     */
    @ResponseStatus(value = HttpStatus.OK)
    @GetMapping(value = "getHouseById", produces = { MediaType.APPLICATION_JSON })
    public House getHouseById(@RequestParam("id") int id) {
        log.debug(() -> "Enter.");

        return HouseDao.getHouseById(id);
    }

}
