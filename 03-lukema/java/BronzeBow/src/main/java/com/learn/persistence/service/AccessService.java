package com.learn.persistence.service;


import java.util.Date;
import java.util.List;

import com.learn.persistence.bean.AccessAlarm;
import com.learn.persistence.bean.AccessHotList;
import com.learn.persistence.bean.AccessRecord;
import com.learn.persistence.bean.BeanBase;


public interface AccessService
{
   public int getRecentAccessCount(String remoteAddress, Date from)
      throws AppException;

   public boolean isAccessBlocked(String remoteAddress)
      throws AppException;

   public <T extends BeanBase> T saveOrUpdate(T bean)
      throws AppException;

   public List<AccessHotList> retrieveAccessHotList(int hotListSize)
      throws AppException;

   public AccessHotList findAccessHotListBySymbol(String symbol)
      throws AppException;

   public <T extends BeanBase> T save(T bean)
      throws AppException;

   public <T extends BeanBase> T update(T bean)
      throws AppException;

   public void shrinkAccessRecords()
      throws AppException;

   public void shrinkAccessAlarms()
      throws AppException;

   public AccessAlarm saveAccessAlarm(String remoteAddress, String symbol)
      throws AppException;

   public AccessRecord saveAccessRecord(String remoteAddress, String symbol)
      throws AppException;

   public void updateAccessHotList(String symbol)
      throws AppException;
}
