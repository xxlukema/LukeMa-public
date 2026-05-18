package com.learn.persistence.service.impl;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.learn.persistence.bean.AccessAlarm;
import com.learn.persistence.bean.AccessHotList;
import com.learn.persistence.bean.AccessRecord;
import com.learn.persistence.bean.BeanBase;
import com.learn.persistence.dao.AccessDAO;
import com.learn.persistence.service.AccessService;
import com.learn.persistence.service.AppException;


public class AccessServiceImpl
   implements AccessService
{
   protected static final Logger LOG = Logger.getLogger(AccessServiceImpl.class);

   private AccessDAO             accessDAO;

   public <T extends BeanBase> T saveOrUpdate(T bean)
      throws AppException
   {
      return getAccessDAO().saveOrUpdate(bean);
   }

   public int getRecentAccessCount(String remoteAddress, Date from)
      throws AppException
   {
      return getAccessDAO().getRecentAccessCount(remoteAddress, from);
   }

   public boolean isAccessBlocked(String remoteAddress)
      throws AppException
   {
      return getAccessDAO().isAccessBlocked(remoteAddress);
   }

   public void setAccessDAO(AccessDAO accessDAO)
   {
      this.accessDAO = accessDAO;
   }

   public AccessDAO getAccessDAO()
   {
      return accessDAO;
   }

   public List<AccessHotList> retrieveAccessHotList(int hotListSize)
      throws AppException
   {
      return getAccessDAO().retrieveAccessHotList(hotListSize);
   }

   public <T extends BeanBase> T save(T bean)
      throws AppException
   {
      return getAccessDAO().save(bean);
   }

   public <T extends BeanBase> T update(T bean)
      throws AppException
   {
      return getAccessDAO().update(bean);
   }

   public void shrinkAccessRecords()
      throws AppException
   {
      getAccessDAO().shrinkAccessRecords();
   }

   public void shrinkAccessAlarms()
      throws AppException
   {
      getAccessDAO().shrinkAccessAlarms();
   }

   public AccessAlarm saveAccessAlarm(String remoteAddress, String symbol)
      throws AppException
   {
      AccessAlarm accessAlarm = new AccessAlarm();
      accessAlarm.setDateCreated(new Date());
      accessAlarm.setRemoteAddress(remoteAddress);
      accessAlarm.setSymbol(symbol);

      getAccessDAO().saveOrUpdate(accessAlarm);

      return accessAlarm;
   }

   public AccessRecord saveAccessRecord(String remoteAddress, String symbol)
      throws AppException
   {
      AccessRecord accessRecord = new AccessRecord();
      accessRecord.setDateCreated(new Date());
      accessRecord.setRemoteAddress(remoteAddress);
      accessRecord.setSymbol(symbol);

      getAccessDAO().saveOrUpdate(accessRecord);

      return accessRecord;
   }

   public void updateAccessHotList(String symbol)
      throws AppException
   {
      getAccessDAO().updateAccessHotList(symbol);
   }

   public AccessHotList findAccessHotListBySymbol(String symbol)
      throws AppException
   {
      try
      {
         return getAccessDAO().findAccessHotListBySymbol(symbol);
      }
      catch (Exception e)
      {
         throw new AppException(e);
      }
   }
}
