package com.learn.dao.test;


import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.learn.boot.config.BootJpaConfig;
import com.learn.pojo.CurrentDatePojo;
import com.learn.repository.NamedParameterJdbcTemplateDao;

import lombok.extern.log4j.Log4j2;


@ContextConfiguration(classes = { BootJpaConfig.class })
@SpringBootTest
@Log4j2
class JdbcDaoTests {

    @Autowired
    private NamedParameterJdbcTemplateDao namedParameterJdbcTemplateDao;

    @Test
    void testSelectCurrentDateEntityManager() {
        log.debug(() -> "Begin test.");

        CurrentDatePojo currentDatePojo = namedParameterJdbcTemplateDao.selectCurrentDateEntityManager();

        log.info(currentDatePojo);

        assertNotNull(currentDatePojo);

        log.debug(() -> "End test.");
    }

    @Test
    void testSelectCurrentDateJdbcTemplate() {
        log.debug(() -> "Begin test.");

        CurrentDatePojo currentDatePojo = namedParameterJdbcTemplateDao.selectCurrentDateJdbcTemplate();

        log.info(currentDatePojo);

        assertNotNull(currentDatePojo);

        log.debug(() -> "End test.");
    }

}
