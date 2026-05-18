package com.learn.service.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.dao.CommonDAO;
import com.learn.exception.AppException;
import com.learn.pojo.CurrentDatePojo;
import com.learn.service.DbPingService;


@Service("dbPingService")
@Transactional
public class DbPingServiceImpl
    implements DbPingService {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    // @Qualifier("commonDAO")
    private CommonDAO commonDAO;

    @Override
    public CurrentDatePojo selectCurrentDate()
        throws AppException {
        CurrentDatePojo currentDate = commonDAO.selectCurrentDate();
        LOG.info("currentDate=" + currentDate);
        return currentDate;
    }

}
