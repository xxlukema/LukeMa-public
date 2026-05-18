package com.learn.spring;


import java.util.List;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.learn.spring.rowmapper.ParamsRow;
import com.learn.spring.rowmapper.ParamsRowMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:SpringBeanConfig.xml" })
@Transactional
public class SelectParamsJdbcTemplateTest {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //@Ignore
    @Test
    public void testQueryEmployee()
        throws Exception {
        LOG.trace("Begin Test");

        Object[] args = new Object[1];
        args[0] = "BLOG_PUBLISHERS";

        List<ParamsRow> list = jdbcTemplate.query(ParamsRowMapper.SQL_SELECT_EMP, args, new ParamsRowMapper());

        LOG.debug("list.size() = " + list.size());

        for (ParamsRow row : list) {
            LOG.debug("Name: " + row.getName() + ". Age: " + row.getAge());
        }

        LOG.info("Completed.");
    }

    @Ignore
    @Test
    public void testUpdateEmployee()
        throws Exception {
        LOG.info("Begin Test");

        String SQL_update = "update tmp_emp set age = ? where id = ?";

        jdbcTemplate.update(SQL_update, 1, 21);

        LOG.info("Completed.");
    }
}
