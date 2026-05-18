package com.learn.jpa.dao.test;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.learn.boot.config.BootAppConfig;
import com.learn.boot.jpa.dao.NamedQueryJpaDao;
import com.learn.boot.jpa.pojo.SysdatePojo;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BootAppConfig.class })
@SpringBootTest
public class NamedQueryJpaDaoTest {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private NamedQueryJpaDao namedQueryJpaDao;

    private Map<String, Object> namedParameters;

    @Before
    public void before()
        throws Exception {
        LOG.info("before(). For each test.");

        namedParameters = new HashMap<>();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date before = calendar.getTime();

        namedParameters.put("num", 1);
        namedParameters.put("dat", before);
    }

    @Test
    public void testSelectCurrentDateEntityManager() {
        LOG.info("Begin test.");

        SysdatePojo currentDatePojo = namedQueryJpaDao.selectCurrentDateJpa(namedParameters);

        LOG.info(currentDatePojo);

        LOG.info("End test.");
    }

}
