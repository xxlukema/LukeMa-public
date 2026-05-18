package com.learn.repository;


import com.learn.pojo.CurrentDatePojo;


public interface NamedParameterJdbcTemplateDao {

    public CurrentDatePojo selectCurrentDateEntityManager();

    public CurrentDatePojo selectCurrentDateJdbcTemplate();

}
