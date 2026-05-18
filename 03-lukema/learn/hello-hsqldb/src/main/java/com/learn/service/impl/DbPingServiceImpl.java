package com.learn.service.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.exception.AppException;
import com.learn.pojo.CurrentDatePojo;
import com.learn.repository.CommonRepository;
import com.learn.service.DbPingService;


@Service("dbPingService")
@Transactional
public class DbPingServiceImpl
    implements DbPingService {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    // @Qualifier("commonDAO")
    private CommonRepository commonDao;

    @Override
    public CurrentDatePojo selectCurrentDate()
        throws AppException {
        try {
            CurrentDatePojo currentDate = commonDao.selectCurrentDate();
            log.info("currentDate=" + currentDate);
            return currentDate;
        } catch (Exception e) {
            throw new AppException("selectCurrentDate() Exception", e);
        }
    }

}
