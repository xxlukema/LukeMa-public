package com.learn.spring;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.learn.spring.rowmapper.SysdateRow;
import com.learn.spring.rowmapper.SysdateRowMapper;

import lombok.extern.log4j.Log4j2;


@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration({ "classpath:SpringBeanConfig.xml" })
class SelectSysdateJdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void runTest()
        throws Exception {
        log.info("Begin Test...");

        List<SysdateRow> list = jdbcTemplate.query(SysdateRowMapper.SQL_SELECT_CURRENTDATE, new SysdateRowMapper());

        log.debug("list.size() = " + list.size());

        for (SysdateRow row : list) {
            log.debug(row.getDate());
        }

        log.info("End Test.");
    }
}
