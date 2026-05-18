package com.learn.boot.jdbc.rowmapper.dao.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.learn.boot.jdbc.pojo.SysDateRow;
import com.learn.util.JdbcConstants;


public class SysDateRowMapper
    implements RowMapper<SysDateRow> {

    public static final String SQL_SELECT_SYSDATE_PARM = JdbcConstants.SQL_SELECT_SYSDATE_NAMED_QUERY;

    @Override
    public SysDateRow mapRow(ResultSet rs, int arg1)
        throws SQLException {

        SysDateRow row = new SysDateRow();
        row.setSysDate(rs.getDate("sys_date"));
        row.setNote(rs.getString("note"));

        return row;
    }

}
