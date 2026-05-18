package com.learn.persistence.dao;


import java.util.Date;
import java.util.List;

import com.learn.persistence.bean.AccessHotList;


public interface AccessDAO
   extends CommonDAO
{
   public int getRecentAccessCount(String remoteAddress, Date from);

   public boolean isAccessBlocked(String remoteAddress);

   public List<AccessHotList> retrieveAccessHotList(int hotListSize);

   public AccessHotList findByExample(AccessHotList exampleEntity);

   public void shrinkAccessRecords();

   public void shrinkAccessAlarms();

   public AccessHotList findAccessHotListBySymbol(String symbol)
      throws Exception;

   public void updateAccessHotList(String symbol);
}
