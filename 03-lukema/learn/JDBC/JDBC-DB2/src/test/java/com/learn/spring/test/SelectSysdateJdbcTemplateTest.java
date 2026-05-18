package com.learn.spring.test;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.spring.rowmapper.SysdateRow;
import com.learn.spring.rowmapper.SysdateRowMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({ "classpath:SpringBeanConfig.xml" })
public class SelectSysdateJdbcTemplateTest {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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

        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("num", 1);
        namedParameters.put("dat", before);

        /**
         * SQL and Parameter log level:
         * 
         * TRACE org.springframework.jdbc.core
         * 
         * 2018-03-12 16:21:56 DEBUG org.springframework.jdbc.core.JdbcTemplate(597) execute()
         * Executing prepared SQL statement [SELECT CURRENT date as sysdate FROM SYSIBM.SYSDUMMY1 where 100 != ? and sysdate != ?]
         * 2018-03-12 16:21:56 TRACE org.springframework.jdbc.core.StatementCreatorUtils(222) setParameterValueInternal()
         * Setting SQL statement parameter value: column index 1, parameter value [1], value class [java.lang.Integer], SQL type unknown
         * 2018-03-12 16:21:56 TRACE org.springframework.jdbc.core.StatementCreatorUtils(222) setParameterValueInternal()
         * Setting SQL statement parameter value: column index 2, parameter value [Fri Jan 12 16:21:56 EST 2018], value class [java.util.Date], SQL type unknown
         * 
         */
        List<SysdateRow> list = namedParameterJdbcTemplate.query(SysdateRowMapper.SQL_SELECT_SYSDATE_PARM, namedParameters, new SysdateRowMapper());

        LOG.debug("list.size() = " + list.size());

        for (SysdateRow row : list) {
            LOG.debug(row.getDate());
        }

        LOG.info("Completed.");
    }
}
