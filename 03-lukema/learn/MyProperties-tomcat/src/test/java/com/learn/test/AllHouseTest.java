package com.learn.test;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.bean.House;
import com.learn.dao.HouseDao;


/**
 * https://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/
 * 
 * @author lukema
 *
 */
public class AllHouseTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testInitializeHouses() {

        List<House> list = HouseDao.getAllHouseList();

        ObjectMapper objectMapper = new ObjectMapper();

        list.forEach(item -> {
            try {
                String str = objectMapper.writeValueAsString(item);
                LOG.info(str);
            } catch (JsonProcessingException e) {
                LOG.error("JsonProcessingException", e);
            }
        });

    }

}
