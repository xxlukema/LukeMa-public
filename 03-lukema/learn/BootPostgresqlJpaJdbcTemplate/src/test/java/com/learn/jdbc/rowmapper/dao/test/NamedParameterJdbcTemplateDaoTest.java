package com.learn.jdbc.rowmapper.dao.test;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import com.learn.boot.jdbc.pojo.SysDateRow;
import com.learn.boot.jdbc.rowmapper.dao.NamedParameterJdbcTemplateDao;


@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { BootAppConfig.class })
@SpringBootTest
public class NamedParameterJdbcTemplateDaoTest {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private NamedParameterJdbcTemplateDao namedParameterJdbcTemplateDao;

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
    public void testRowMapper() {
        LOG.info("Begin test.");

        List<SysDateRow> list = namedParameterJdbcTemplateDao.selectCurrentDateJdbcTemplateRowMapper(namedParameters);

        list.forEach(LOG::info);

        LOG.info("End test.");
    }

}
