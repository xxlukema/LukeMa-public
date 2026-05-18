package com.learn.spring;


import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.spring.rowmapper.SysdateRow;
import com.learn.spring.rowmapper.SysdateRowMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:SpringBeanConfig.xml" })
public class SelectSysdateJdbcTemplateTest {
    protected static final Logger LOG = Logger.getLogger(SelectSysdateJdbcTemplateTest.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void runTest()
        throws Exception {
        LOG.info("Begin Test");

        List<SysdateRow> list = jdbcTemplate.query(SysdateRowMapper.SQL_SELECT_SYSDATE, new SysdateRowMapper());

        LOG.debug("list.size() = " + list.size());

        for (SysdateRow row : list) {
            LOG.debug(row.getDate());
        }

        LOG.info("Completed.");
    }
}
