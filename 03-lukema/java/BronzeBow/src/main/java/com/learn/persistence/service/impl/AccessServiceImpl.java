package com.learn.persistence.service.impl;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.persistence.bean.AccessAlarm;
import com.learn.persistence.bean.AccessHotList;
import com.learn.persistence.bean.AccessRecord;
import com.learn.persistence.bean.BeanBase;
import com.learn.persistence.dao.AccessDAO;
import com.learn.persistence.service.AccessService;
import com.learn.persistence.service.AppException;


@Service("accessService")
@Transactional
public class AccessServiceImpl
    implements Serializable, AccessService {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("accessDAO")
    private AccessDAO accessDAO;

    public <T extends BeanBase> T saveOrUpdate(T bean)
        throws AppException {
        return accessDAO.saveOrUpdate(bean);
    }

    public int getRecentAccessCount(String remoteAddress, Date from)
        throws AppException {
        return accessDAO.getRecentAccessCount(remoteAddress, from);
    }

    public boolean isAccessBlocked(String remoteAddress)
        throws AppException {
        return accessDAO.isAccessBlocked(remoteAddress);
    }

    public List<AccessHotList> retrieveAccessHotList(int hotListSize)
        throws AppException {
        return accessDAO.retrieveAccessHotList(hotListSize);
    }

    public <T extends BeanBase> T save(T bean)
        throws AppException {
        return accessDAO.save(bean);
    }

    public <T extends BeanBase> T update(T bean)
        throws AppException {
        return accessDAO.update(bean);
    }

    public void shrinkAccessRecords()
        throws AppException {
        accessDAO.shrinkAccessRecords();
    }

    public void shrinkAccessAlarms()
        throws AppException {
        accessDAO.shrinkAccessAlarms();
    }

    public AccessAlarm saveAccessAlarm(String remoteAddress, String symbol)
        throws AppException {
        AccessAlarm accessAlarm = new AccessAlarm();
        accessAlarm.setCreateDate(new Date());
        accessAlarm.setRemoteAddress(remoteAddress);
        accessAlarm.setSymbol(symbol);

        accessDAO.saveOrUpdate(accessAlarm);

        return accessAlarm;
    }

    public AccessRecord saveAccessRecord(String remoteAddress, String symbol)
        throws AppException {
        AccessRecord accessRecord = new AccessRecord();
        accessRecord.setCreateDate(new Date());
        accessRecord.setRemoteAddress(remoteAddress);
        accessRecord.setSymbol(symbol);

        accessDAO.saveOrUpdate(accessRecord);

        return accessRecord;
    }

    public void updateAccessHotList(String symbol)
        throws AppException {
        accessDAO.updateAccessHotList(symbol);
    }

    public AccessHotList findAccessHotListBySymbol(String symbol)
        throws AppException {
        try {
            return accessDAO.findAccessHotListBySymbol(symbol);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }
}
