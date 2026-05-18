package com.learn.persistence.util;


import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

import com.learn.persistence.bean.AccessAlarm;
import com.learn.persistence.bean.AccessHotList;
import com.learn.persistence.bean.AccessRecord;
import com.learn.persistence.bean.BeanBase;
import com.learn.persistence.bean.User;
import com.learn.persistence.service.AccessService;
import com.learn.persistence.service.AppException;
import com.learn.persistence.service.UserService;


public class SpringServiceFacade
{
   protected static final Logger LOG  = Logger.getLogger(SpringServiceFacade.class);

   private static AccessService  accessService;

   private static UserService    userService;

   private static Lock           LOCK = new ReentrantLock();

   public static AccessService getAccessService()
   {
      if (accessService == null)
      {
         LOCK.lock();
         try
         {
            if (accessService == null)
            {
               accessService = SpringApplicationContext.getAccessService();
            }
         }
         finally
         {
            LOCK.unlock();
         }
      }

      return accessService;
   }

   public static void setAccessService(AccessService accessService)
   {
      SpringServiceFacade.accessService = accessService;
   }

   public static UserService getUserService()
   {
      if (userService == null)
      {
         LOCK.lock();
         try
         {
            if (userService == null)
            {
               userService = SpringApplicationContext.getUserService();
            }
         }
         finally
         {
            LOCK.unlock();
         }
      }

      return userService;
   }

   public static void setUserService(UserService userService)
   {
      SpringServiceFacade.userService = userService;
   }

   public static int getRecentAccessCount(String remoteAddress, Date from)
      throws AppException
   {
      return getAccessService().getRecentAccessCount(remoteAddress, from);
   }

   public static boolean isAccessBlocked(String remoteAddress)
      throws AppException
   {
      return getAccessService().isAccessBlocked(remoteAddress);
   }

   public static AccessAlarm putToAccessAlarm(String remoteAddress, String symbol)
      throws AppException
   {
      return getAccessService().saveAccessAlarm(remoteAddress, symbol);
   }

   public static AccessRecord recordAccess(String remoteAddress, String symbol)
      throws AppException
   {
      return getAccessService().saveAccessRecord(remoteAddress, symbol);
   }

   public static <T extends BeanBase> T saveOrUpdate(T bean)
      throws AppException
   {
      return getAccessService().saveOrUpdate(bean);
   }

   public static void updateAccessHotList(String symbol)
      throws AppException
   {
      getAccessService().updateAccessHotList(symbol);
   }

   public static List<AccessHotList> retrieveAccessHotList(int hotListSize)
      throws AppException
   {
      return getAccessService().retrieveAccessHotList(hotListSize);
   }

   public static User getUserByUsername(String username)
      throws AppException
   {
      return getUserService().getUserByUsername(username);
   }

   public static User getUserByEmail(String email)
      throws AppException
   {
      return getUserService().getUserByEmail(email);
   }

   public static void doDatabaseHouseKeeping()
   {
      try
      {
         getAccessService().shrinkAccessRecords();
         getAccessService().shrinkAccessAlarms();
      }
      catch (Exception e)
      {
         LOG.error("Exception clean up AccessReords table.", e);
      }
   }
}
