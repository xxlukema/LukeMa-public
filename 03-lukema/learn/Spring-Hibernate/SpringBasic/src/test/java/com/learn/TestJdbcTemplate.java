package com.learn;


import java.sql.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:SpringBeanConfig.xml" })
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = true)
@Transactional
public class TestJdbcTemplate {

    protected static final Logger LOG = Logger.getLogger(TestJdbcTemplate.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testJdbc() {
        LOG.info("Begining of Test");

        String sql = "select sysdate from dual";

        Date date = jdbcTemplate.queryForObject(sql, Date.class);

        LOG.info("date = " + date);

        LOG.info("End of Test");
    }

}
