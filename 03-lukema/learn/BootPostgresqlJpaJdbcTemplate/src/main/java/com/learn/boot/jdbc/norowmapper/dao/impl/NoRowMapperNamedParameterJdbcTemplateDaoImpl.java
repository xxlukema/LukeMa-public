package com.learn.boot.jdbc.norowmapper.dao.impl;


import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.learn.boot.jdbc.norowmapper.dao.NoRowMapperNamedParameterJdbcTemplateDao;
import com.learn.boot.jdbc.pojo.SysDateRow;
import com.learn.boot.jdbc.rowmapper.dao.rowmapper.SysDateRowMapper;


/**
 * SQL and Parameter log level:
 * 
 * TRACE org.springframework.jdbc.core
 * 
 * 2018-06-21 17:11:48 TRACE org.springframework.jdbc.core.StatementCreatorUtils(222) setParameterValueInternal() 
 * Setting SQL statement parameter value: column index 1, parameter value [1], value class [java.lang.Integer], SQL type unknown
 * 2018-06-21 17:11:48 TRACE org.springframework.jdbc.core.StatementCreatorUtils(222) setParameterValueInternal()
 * Setting SQL statement parameter value: column index 2, parameter value [Sat Apr 21 17:11:48 EDT 2018], value class [java.util.Date], SQL type unknown
 * 2018-06-21 17:11:48 DEBUG org.springframework.jdbc.core.BeanPropertyRowMapper(300) mapRow()
 * Mapping column 'sys_date' to property 'sysDate' of type 'java.util.Date'
 * 2018-06-21 17:11:48 DEBUG org.springframework.jdbc.core.BeanPropertyRowMapper(300) mapRow()
 * Mapping column 'note' to property 'note' of type 'java.lang.String' 
 *  
 */
@Repository("noRowMapperNamedParameterJdbcTemplateDao")
public class NoRowMapperNamedParameterJdbcTemplateDaoImpl
    implements NoRowMapperNamedParameterJdbcTemplateDao {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * (1) Column values are mapped based on matching the column name as obtained from result set meta-data to public setters 
     *     for the corresponding properties. The names are matched either directly or by transforming a name separating the 
     *     parts with underscores to the same name using "camel" case.
     *
     * (2) To facilitate mapping between columns and fields that don't have matching names, try using column aliases in the 
     *     SQL statement like 
     *     "select fname (column name) as first_name (column label) from customer" or 
     *     "select fname as firstName from customer" or --- The column name or column label is non-case sensitive. 
     *                                                  --- It will be converted to lower case and mapped to property name. 
     *     "select fname as firstnAME from customer"    --- Example: Mapping column 'firstnAME' to property 'firstName' of type 'com.learn.Name'
     *     first_name will be mapped to firstName.
     * 
     */
    @Override
    public List<SysDateRow> selectCurrentDateJdbcTemplateBeanPropertyRowMapper(Map<String, Object> namedParameters) {

        LOG.info("Entering function.");

        List<SysDateRow> list = namedParameterJdbcTemplate.query(SysDateRowMapper.SQL_SELECT_SYSDATE_PARM, namedParameters,
                new BeanPropertyRowMapper<>(SysDateRow.class));

        LOG.debug("list.size() = " + list.size());

        return list;
    }

}
