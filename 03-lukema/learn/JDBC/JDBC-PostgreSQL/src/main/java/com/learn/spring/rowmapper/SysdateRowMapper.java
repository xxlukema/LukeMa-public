package com.learn.spring.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.learn.util.StringConstants;

import lombok.extern.log4j.Log4j2;


@Log4j2
public class SysdateRowMapper
    implements RowMapper<SysdateRow> {

    public static final String SQL_SELECT_CURRENTDATE = StringConstants.SQL_SELECT_CURRENTDATE;

    @Override
    public SysdateRow mapRow(ResultSet resultSet, int arg1)
        throws SQLException {

        SysdateRow row = new SysdateRow();

        java.sql.Date sqlDate = resultSet.getDate("date");
        log.debug("sqlDate from resultSet: {}", () -> sqlDate);
        java.util.Date utilDate = new java.util.Date(sqlDate.getTime());

        row.setDate(utilDate);

        return row;
    }

}
