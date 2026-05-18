package com.learn.spring.rowmapper;


import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class ParamsRowMapper
    implements RowMapper<ParamsRow> {

    //public static final String SQL_SELECT_EMP = "select name, age from tmp_emp where id = ?";
    
    public static final String SQL_SELECT_EMP = "SELECT 100 AS age, party_id AS name FROM rm_qa_sa.party WHERE party_id = ?";
    
    //public static final String SQL_SELECT_SYSDATE = "select NUM_CLAIM as name, NUM_TREASURY_CLAIM as age from claim where isn = ?";

    @Override
    public ParamsRow mapRow(ResultSet rs, int arg1)
        throws SQLException {

        ParamsRow row = new ParamsRow();
        row.setName(rs.getString("name"));
        row.setAge(rs.getInt("age"));

        return row;
    }

}
