package com.learn.boot.jdbc.norowmapper.dao;


import java.util.List;
import java.util.Map;

import com.learn.boot.jdbc.pojo.SysDateRow;


public interface NoRowMapperNamedParameterJdbcTemplateDao {

    List<SysDateRow> selectCurrentDateJdbcTemplateBeanPropertyRowMapper(Map<String, Object> namedParameters);

}
