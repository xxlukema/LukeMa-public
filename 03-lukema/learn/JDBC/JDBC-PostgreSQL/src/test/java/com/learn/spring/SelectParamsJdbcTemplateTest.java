package com.learn.spring;


import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import com.learn.spring.rowmapper.ParamsRow;
import com.learn.spring.rowmapper.ParamsRowMapper;

import lombok.extern.log4j.Log4j2;


@Log4j2
@ExtendWith(SpringExtension.class)
@Transactional
class SelectParamsJdbcTemplateTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    //@Ignore
    @Test
    void runTest()
        throws Exception {
        log.trace("Begin Test");

        Object[] args = new Object[1];
        args[0] = "BLOG_PUBLISHERS";

        List<ParamsRow> list = jdbcTemplate.query(ParamsRowMapper.SQL_SELECT_EMP, new ParamsRowMapper(), args);

        log.debug("list.size() = " + list.size());

        for (ParamsRow row : list) {
            log.debug("Name: " + row.getName() + ". Age: " + row.getAge());
        }

        log.info("Completed.");
    }

    @Test
    void runUpdate()
        throws Exception {
        log.info("Begin Test");

        String update = "update tmp_emp set age = ? where id = ?";

        jdbcTemplate.update(update, 1, 21);

        log.info("Completed.");
    }
}
