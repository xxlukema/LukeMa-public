package com.learn.boot.jdbc.rowmapper.dao.impl;


import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.learn.boot.jdbc.pojo.SysDateRow;
import com.learn.boot.jdbc.rowmapper.dao.NamedParameterJdbcTemplateDao;
import com.learn.boot.jdbc.rowmapper.dao.rowmapper.SysDateRowMapper;


/**
 * SQL and Parameter log level:
 * 
 * TRACE org.springframework.jdbc.core
 * 
 * 2018-06-20 15:11:20 DEBUG org.springframework.jdbc.core.JdbcTemplate(597) execute()
 * Executing prepared SQL statement [SELECT current_date as sysdate where 100 != ? and current_date != ?]
 * 2018-06-20 15:11:20 TRACE org.springframework.jdbc.core.StatementCreatorUtils(222) setParameterValueInternal()
 * Setting SQL statement parameter value: column index 1, parameter value [1], value class [java.lang.Integer], SQL type unknown
 * 2018-06-20 15:11:20 TRACE org.springframework.jdbc.core.StatementCreatorUtils(222) setParameterValueInternal()
 * Setting SQL statement parameter value: column index 2, parameter value [Fri Apr 20 15:11:20 EDT 2018], value class [java.util.Date], SQL type unknown
 * 
 */
@Repository("namedParameterJdbcTemplateDao")
public class NamedParameterJdbcTemplateDaoImpl
    implements NamedParameterJdbcTemplateDao {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    /**
     * (1) Column values are mapped based on matching the column name as obtained from result set meta-data to public setters 
     *     for the corresponding properties. The names are matched either directly or by transforming a name separating the 
     *     parts with underscores to the same name using "camel" case.
     *
     * (2) To facilitate mapping between columns and fields that don't have matching names, try using column aliases in the 
     *     SQL statement like "select fname as first_name from customer". first_name will be mapped to firstName.
     * 
     */
    @Override
    public List<SysDateRow> selectCurrentDateJdbcTemplateRowMapper(Map<String, Object> namedParameters) {

        LOG.info("Entering function.");

        // List<SysDateRow> list = namedParameterJdbcTemplate.query(SysDateRowMapper.SQL_SELECT_SYSDATE_PARM, namedParameters, new SysDateRowMapper());

        /**
         * http://www.baeldung.com/java-8-lambda-expressions-tips
         * 8.1. Avoid Blocks of Code in Lambda’s Body
         *      If you have a large block of code, the lambda’s functionality is not immediately clear.
         *      
         *      SysDateRowMapper.java is more preferred in this case.
         */
        RowMapper<SysDateRow> rowMapper = (rs, arg1) -> {
            SysDateRow row = new SysDateRow();
            row.setSysDate(rs.getDate("sys_date"));
            row.setNote(rs.getString("note"));

            return row;
        };

        List<SysDateRow> list = namedParameterJdbcTemplate.query(SysDateRowMapper.SQL_SELECT_SYSDATE_PARM, namedParameters, rowMapper);

        LOG.debug("list.size() = " + list.size());

        return list;
    }
}
