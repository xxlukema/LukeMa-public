package com.learn.persistence.dao.impl;


import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.learn.persistence.bean.AccessBlocked;
import com.learn.persistence.bean.AccessHotList;
import com.learn.persistence.dao.AccessDAO;


public class AccessDAOImpl
   extends CommonDAOImpl
   implements AccessDAO
{
   protected static final Logger               LOG                            = Logger.getLogger(AccessDAOImpl.class);

   private static final String                 RecentAccessCountQueryName     = "getRecentAccessCount";

   private static final String                 RetrieveAccessHotListQueryName = "retrieveAccessHotList";

   private static final String                 FindAccessHotListBySymbol      = "findAccessHotListBySymbol";

   private static final String[]               RecentAccessCountParamNames    = { "remoteAddress", "date" };

   private AccessRecordsCleanupStoredProcedure accessRecordsCleanupStoredProcedure;

   private AccessAlarmsCleanupStoredProcedure  accessAlarmsCleanupStoredProcedure;

   public int getRecentAccessCount(String remoteAddress, Date from)
   {
      Object[] values = { remoteAddress, from };

      @SuppressWarnings("unchecked")
      List<Long> list = (List<Long>) getHibernateTemplate().findByNamedQueryAndNamedParam(RecentAccessCountQueryName, RecentAccessCountParamNames, values);

      return list.get(0).intValue();
   }

   public boolean isAccessBlocked(String remoteAddress)
   {
      AccessBlocked exampleEntity = new AccessBlocked();
      exampleEntity.setRemoteAddress(remoteAddress);

      List<AccessBlocked> list = (List<AccessBlocked>) getHibernateTemplate().findByExample(exampleEntity);

      return list.size() > 0;
   }

   public List<AccessHotList> retrieveAccessHotList(int hotListSize)
   {
      getHibernateTemplate().setMaxResults(hotListSize);
      @SuppressWarnings("unchecked")
      List<AccessHotList> list = (List<AccessHotList>) getHibernateTemplate().findByNamedQuery(RetrieveAccessHotListQueryName);

      return list;
   }

   public AccessHotList findByExample(AccessHotList exampleEntity)
   {
      @SuppressWarnings("unchecked")
      List<AccessHotList> list = (List<AccessHotList>) getHibernateTemplate().find("from AccessHotList where symbol = ?", exampleEntity.getSymbol());
      if (list.size() > 0)
      {
         return list.get(0);
      }
      else
      {
         return null;
      }
   }

   public void shrinkAccessRecords()
   {
      accessRecordsCleanupStoredProcedure.execute();
   }

   public void shrinkAccessAlarms()
   {
      accessAlarmsCleanupStoredProcedure.execute();
   }

   public void setAccessRecordsCleanupStoredProcedure(AccessRecordsCleanupStoredProcedure accessRecordsCleanupStoredProcedure)
   {
      this.accessRecordsCleanupStoredProcedure = accessRecordsCleanupStoredProcedure;
   }

   public AccessRecordsCleanupStoredProcedure getAccessRecordsCleanupStoredProcedure()
   {
      return accessRecordsCleanupStoredProcedure;
   }

   public void setAccessAlarmsCleanupStoredProcedure(AccessAlarmsCleanupStoredProcedure accessAlarmsCleanupStoredProcedure)
   {
      this.accessAlarmsCleanupStoredProcedure = accessAlarmsCleanupStoredProcedure;
   }

   public AccessAlarmsCleanupStoredProcedure getAccessAlarmsCleanupStoredProcedure()
   {
      return accessAlarmsCleanupStoredProcedure;
   }

   public AccessHotList findAccessHotListBySymbol(String symbol)
      throws Exception
   {
      @SuppressWarnings("unchecked")
      List<AccessHotList> list = (List<AccessHotList>) getHibernateTemplate().findByNamedQueryAndNamedParam(FindAccessHotListBySymbol, "symbol", symbol);

      if (list.size() == 1)
      {
         return (AccessHotList) list.get(0);
      }
      else if (list.size() > 1)
      {
         throw new Exception("AccessHotList cannot have duplicated symbol.");
      }

      return null;
   }

   public void updateAccessHotList(String symbol)
   {
      AccessHotList accessHotList = null;
      try
      {
         accessHotList = findAccessHotListBySymbol(symbol);
      }
      catch (Exception e)
      {
      }

      if (accessHotList == null)
      {
         accessHotList = new AccessHotList();
         accessHotList.setSymbol(symbol);
         accessHotList.setDateUpdated(new Date());
         accessHotList.setAccessCounter(1);
         save(accessHotList);
      }
      else
      {
         accessHotList.setDateUpdated(new Date());
         accessHotList.setAccessCounter(accessHotList.getAccessCounter() + 1);
         update(accessHotList);
      }
   }
}
