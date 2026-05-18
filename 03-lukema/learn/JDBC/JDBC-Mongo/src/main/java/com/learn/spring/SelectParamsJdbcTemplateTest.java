package com.learn.spring;


import java.util.List;

import org.apache.log4j.Logger;
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
    protected static final Logger LOG = Logger.getLogger(SelectParamsJdbcTemplateTest.class);

    //protected Log LOG = LogFactory.getLog(SelectParamsJdbcTemplateTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //@Ignore
    @Test
    public void runTest()
        throws Exception {
        LOG.trace("Begin Test");

        Object[] args = new Object[1];
        args[0] = 1;

        List<ParamsRow> list = jdbcTemplate.query(ParamsRowMapper.SQL_SELECT_SYSDATE, args, new ParamsRowMapper());

        LOG.debug("list.size() = " + list.size());

        for (ParamsRow row : list) {
            LOG.debug("Name: " + row.getName() + ". Age: " + row.getAge());
        }

        LOG.info("Completed.");
    }

    //@Ignore
    @Test
    public void runUpdate()
        throws Exception {
        LOG.info("Begin Test");

        String SQL_update = "update tmp_emp set age = ? where id = ?";

        jdbcTemplate.update(SQL_update, 1, 21);

        LOG.info("Completed.");
    }
}
