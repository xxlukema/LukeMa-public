package com.learn.spring;


import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.learn.util.StringConstants;

import lombok.extern.log4j.Log4j2;


@Log4j2
@ExtendWith(SpringExtension.class)
@ContextConfiguration({ "classpath:SpringBeanConfig.xml" })
class SelectSysdateDataSourceTest {

    @Autowired
    @Qualifier(value = "dataSourceLocal")
    private DataSource dataSource;

    @Test
    void testSpring()
        throws Exception {
        log.info("Begin Test...");

        try (Connection connection = dataSource.getConnection();
                PreparedStatement select = connection.prepareStatement(StringConstants.SQL_SELECT_CURRENTDATE);) {

            try (ResultSet resultSet = select.executeQuery();) {

                while (resultSet.next()) {
                    Date date = resultSet.getDate("date");

                    log.info("Date: " + date);
                }
            }

        } catch (Exception e) {
            log.error("Unable to update table.", e);
        }

        log.info("End Test.");
    }
}
