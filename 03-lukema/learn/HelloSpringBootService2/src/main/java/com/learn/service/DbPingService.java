package com.learn.service;


import com.learn.exception.AppException;
import com.learn.pojo.CurrentDatePojo;


public interface DbPingService {

    public CurrentDatePojo selectCurrentDate()
        throws AppException;

}
