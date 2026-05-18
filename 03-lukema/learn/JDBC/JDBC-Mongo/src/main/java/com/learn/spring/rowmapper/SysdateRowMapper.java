package com.learn.spring.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class SysdateRowMapper
    implements RowMapper<SysdateRow> {

    public static final String SQL_SELECT_SYSDATE = "select sysdate from dual";

    @Override
    public SysdateRow mapRow(ResultSet rs, int arg1)
        throws SQLException {

        SysdateRow row = new SysdateRow();
        row.setDate(rs.getDate(1));

        return row;
    }

}
