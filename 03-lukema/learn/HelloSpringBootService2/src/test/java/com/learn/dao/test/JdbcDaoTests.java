package com.learn.dao.test;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.learn.boot.config.BootJpaConfig;
import com.learn.dao.NamedParameterJdbcTemplateDao;
import com.learn.pojo.CurrentDatePojo;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BootJpaConfig.class })
@SpringBootTest
public class JdbcDaoTests {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private NamedParameterJdbcTemplateDao namedParameterJdbcTemplateDao;

    @Test
    public void testSelectCurrentDateEntityManager() {
        LOG.info("Begin test.");

        CurrentDatePojo currentDatePojo = namedParameterJdbcTemplateDao.selectCurrentDateEntityManager();

        LOG.info(currentDatePojo);

        LOG.info("End test.");
    }

    @Test
    public void testSelectCurrentDateJdbcTemplate() {
        LOG.info("Begin test.");

        CurrentDatePojo currentDatePojo = namedParameterJdbcTemplateDao.selectCurrentDateJdbcTemplate();

        LOG.info(currentDatePojo);

        LOG.info("End test.");
    }

}
