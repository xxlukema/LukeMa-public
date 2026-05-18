package com.learn.dao.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.learn.util.JdbcConstants;


public class SysdateRowMapper
    implements RowMapper<SysdateRow> {

    public static final String SQL_SELECT_SYSDATE = JdbcConstants.SQL_SELECT_SYSDATE;

    public static final String SQL_SELECT_SYSDATE_PARM = JdbcConstants.SQL_SELECT_SYSDATE_PARM;

    @Override
    public SysdateRow mapRow(ResultSet rs, int arg1)
        throws SQLException {

        SysdateRow row = new SysdateRow();
        row.setDate(rs.getDate(1));

        return row;
    }

}
