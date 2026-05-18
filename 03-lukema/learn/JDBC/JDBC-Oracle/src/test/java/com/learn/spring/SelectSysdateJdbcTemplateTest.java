package com.learn.spring;


import java.sql.Types;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameterValue;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.spring.rowmapper.SysdateRow;
import com.learn.spring.rowmapper.SysdateRowMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:SpringBeanConfig.xml" })
public class SelectSysdateJdbcTemplateTest {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterClass
    public static void afterClass() {
        // SLF4JBridgeHandler.removeHandlersForRootLogger();
        // SLF4JBridgeHandler.install();
    }

    @Test
    public void testQuery()
        throws Exception {
        LOG.info("Begin Test");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date before = calendar.getTime();
        //java.sql.Date sqlDate = new java.sql.Date(before.getTime());

        SqlParameterValue number = new SqlParameterValue(Types.INTEGER, "num", 1);
        SqlParameterValue date = new SqlParameterValue(Types.DATE, "dat", before);

        List<SysdateRow> list = jdbcTemplate.query(SysdateRowMapper.SQL_SELECT_SYSDATE_PARM, new SysdateRowMapper(), number, date);

        LOG.debug("list.size() = " + list.size());

        for (SysdateRow row : list) {
            LOG.debug(row.getDate());
        }

        LOG.info("Completed.");
    }
}
