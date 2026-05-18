package com.learn.test;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.boot.config.MyPropertiesAppConfig;
import com.learn.dao.HouseDao;
import com.learn.pojo.House;

import lombok.extern.log4j.Log4j2;


/**
 * https://www.mkyong.com/java/jackson-2-convert-java-object-to-from-json/
 *
 * @author lukema
 *
 */
@Log4j2
@ContextConfiguration(classes = { MyPropertiesAppConfig.class })
@SpringBootTest
class AllHouseTest {

    @Test
    void testInitializeHouses() {

        log.debug(() -> "Test start");

        List<House> list = HouseDao.getAllHouseList();

        ObjectMapper objectMapper = new ObjectMapper();

        list.forEach(item -> {
            try {
                String str = objectMapper.writeValueAsString(item);
                log.info(() -> str);
            } catch (JsonProcessingException e) {
                log.error(() -> "JsonProcessingException", e);
            }
        });

        log.debug(() -> "Test end");
    }

}
