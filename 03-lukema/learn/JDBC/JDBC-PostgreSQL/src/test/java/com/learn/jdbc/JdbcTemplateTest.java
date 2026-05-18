package com.learn.jdbc;


import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.learn.spring.rowmapper.SysdateRow;
import com.learn.spring.rowmapper.SysdateRowMapper;
import com.learn.util.StringConstants;

import lombok.extern.log4j.Log4j2;


@Log4j2
@ExtendWith(value = SpringExtension.class)
@EnableAutoConfiguration
class JdbcTemplateTest {

    @Autowired
    DataSource dataSource;

    @Test
    void testSpring()
        throws Exception {
        log.info("Begin Test...");

        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        List<SysdateRow> list = jdbcTemplate.query(StringConstants.SQL_SELECT_CURRENTDATE, new SysdateRowMapper());
        log.debug("Date list: {}", () -> list);

        log.info("End Test.");
    }

}
