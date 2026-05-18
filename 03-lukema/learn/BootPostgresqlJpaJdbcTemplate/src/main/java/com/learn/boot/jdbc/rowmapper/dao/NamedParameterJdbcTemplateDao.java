package com.learn.boot.jdbc.rowmapper.dao;


import java.util.List;
import java.util.Map;

import com.learn.boot.jdbc.pojo.SysDateRow;


public interface NamedParameterJdbcTemplateDao {

    List<SysDateRow> selectCurrentDateJdbcTemplateRowMapper(Map<String, Object> namedParameters);

}
