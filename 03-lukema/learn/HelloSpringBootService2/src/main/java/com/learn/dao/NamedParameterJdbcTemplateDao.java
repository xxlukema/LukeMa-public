package com.learn.dao;


import com.learn.pojo.CurrentDatePojo;


public interface NamedParameterJdbcTemplateDao {

    public CurrentDatePojo selectCurrentDateEntityManager();
    
    public CurrentDatePojo selectCurrentDateJdbcTemplate();
    

}
