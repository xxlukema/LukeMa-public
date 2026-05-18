package com.learn.service;


import org.springframework.stereotype.Service;

import com.learn.exception.AppException;
import com.learn.pojo.CurrentDatePojo;


@Service
public interface DbPingService {

    public CurrentDatePojo selectCurrentDate()
        throws AppException;

}
