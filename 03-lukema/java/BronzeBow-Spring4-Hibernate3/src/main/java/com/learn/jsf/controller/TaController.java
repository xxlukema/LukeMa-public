package com.learn.jsf.controller;


import java.io.File;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;

import com.learn.common.domain.DataRequest;
import com.learn.common.domain.DataResponse;
import com.learn.common.domain.Dividend;
import com.learn.common.util.DataStreamer;
import com.learn.common.util.MbaUtils;
import com.learn.core.util.DomainDataUtils;
import com.learn.jsf.util.AccessUtils;
import com.learn.jsf.util.HotListRow;
import com.learn.jsf.util.JsfUtils;
import com.learn.jsf.util.PageUrls;
import com.learn.persistence.bean.AccessHotList;
import com.learn.persistence.service.AppException;
import com.learn.persistence.util.AccessComparator;
import com.learn.persistence.util.SpringServiceFacade;


@ManagedBean
@SessionScoped
public class TaController
   extends BaseController
{
   private static final long serialVersionUID = 1L;

   protected static final Logger LOG = Logger.getLogger(TaController.class);

   private static final String DefaultSymbol = MbaUtils.DefaultSymbol;
   private static String DefaultChartData;
   private static Lock LOCK = new ReentrantLock();
   private String symbol;
   private String chartData;
   private String dividendData;
   private final String symbolString = "symbol";
   private static int accessHotListCounter = 0;
   private List<HotListRow> myListRowList;
   private List<HotListRow> hotListRowList;
   private static final int HotListSize = 99;
   private static final int HotListRefreshCounter = 5;
   private static final AccessComparator accessComparator = new AccessComparator();
   private static int idCounter = 0;
   private int id = 0;
   private Date creationTime;
   private long lastAccessedMilisec;

   @PostConstruct
   public void init()
   {
      id = idCounter++;

      creationTime = new Date();

      LOG.info("TaController constructor. id: " + id + " Created at: " + creationTime);

      symbol = DefaultSymbol;

      refreshDefaultChartData();

      chartData = DefaultChartData;
      lastAccessedMilisec = System.currentTimeMillis();

      String remoteAddress = null;
      try
      {
         remoteAddress = JsfUtils.getRemoteAddress();
      }
      catch (UnknownHostException e)
      {
         LOG.error("Unable to get remote address.", e);
      }

      try
      {
         AccessUtils.checkAccess(remoteAddress, symbol);
      }
      catch (Exception e)
      {
         LOG.error("Exception with checkAccess.", e);
      }

      getUserLoginController().loginUserFromCookie();

      LOG.info("isUserLoggedIn: " + getUserLoginController().isUserLoggedIn());
   }

   @PreDestroy
   protected void destroy()
   {
      long inactiveTimeMilisec = System.currentTimeMillis() - lastAccessedMilisec;
      int seconds = (int) (inactiveTimeMilisec / 1000);
      int hours = seconds / 3600;
      int minutes = seconds % 3600 / 60;

      LOG.info("TaController destroyed. id: " + id + " Created at: " + creationTime + ". Destroyed at: "
            + new Date() + ". Inactive time: " + hours + " hours " + minutes + " minutes");
   }

   protected void finalize()
   {
      LOG.info("TaController finalized. id: " + id + " Created at: " + creationTime + ". Finalized at: "
            + new Date());
   }

   private void refreshDefaultChartData()
   {
      try
      {
         boolean locked = LOCK.tryLock(3, TimeUnit.SECONDS);

         if (locked)
         {
            boolean isDefaultChartDataUptodate = false;

            if (DefaultChartData != null)
            {
               symbol = MbaUtils.formalizeSysmbol(DefaultSymbol);
               File csvFile = MbaUtils.getCsvFile(symbol);
               isDefaultChartDataUptodate = MbaUtils.isCsvFileUptodate(csvFile);
            }

            if (DefaultChartData == null || !isDefaultChartDataUptodate)
            {
               DataRequest dataRequest = new DataRequest();
               dataRequest.setSymbol(symbol);

               DataResponse dataResponse = DomainDataUtils.doRequest(dataRequest);

               LOG.info("Success loading DataResponse.");

               try
               {
                  DefaultChartData = DataStreamer.objectSerializeToHexString(dataResponse);
               }
               catch (Exception e)
               {
                  LOG.error("Exception initializing chartData.", e);
               }
            }
         }
      }
      catch (InterruptedException e)
      {
         LOG.error("LOCK interrupted.", e);
      }
      catch (Throwable e)
      {
         LOG.error("Exception initializing chartData.", e);
      }
      finally
      {
         try
         {
            LOCK.unlock();
         }
         catch (Throwable t)
         {
            LOG.error("Exception unlock.", t);
         }
      }
   }

   public void setSymbol(String symbol)
   {
      this.symbol = symbol;
   }

   public String getSymbol()
   {
      return symbol;
   }

   public String drawChart()
   {
      LOG.debug("symbol = " + symbol);

      long start = System.currentTimeMillis();

      lastAccessedMilisec = start;

      DataResponse dataResponse = null;
      try
      {
         String remoteAddress = JsfUtils.getRemoteAddress();
         AccessUtils.checkAccess(remoteAddress, symbol);

         DataRequest dataRequest = new DataRequest();
         dataRequest.setSymbol(symbol);

         dataResponse = DomainDataUtils.doRequest(dataRequest);

         // LOG.debug("Success loading DataResponse.");

         chartData = DataStreamer.objectSerializeToHexString(dataResponse);

         Vector<Dividend> list = DomainDataUtils.getDividendData(symbol);
         dividendData = DataStreamer.objectSerializeToHexString(list);

         // LOG.debug("chartData length: " + chartData.length());
      }
      catch (Exception e)
      {
         LOG.error("Unable to process the request.", e);
         dataResponse = new DataResponse();
         dataResponse.setSuccess(false);
         dataResponse.setErrorMessage(e.getMessage());
      }
      finally
      {
         float seconds = JsfUtils.timeInSecondsFromStart(start);
         LOG.info("Leaving drawChart. Task completed in " + seconds + " seconds for ticker: " + symbol);
      }

      return PageUrls.Ta;
   }

   public void symbolLinkClickedActionListener(ActionEvent actionEvent)
   {
      LOG.debug("Inside symbolLinkClickedActionListener actionListener of Controller.");

      String symbol = JsfUtils.getAttributeFromFacesEvent(getSymbolString(), actionEvent);

      setSymbol(symbol);

      LOG.debug("Symbol set to: " + symbol);
   }

   public String getSymbolString()
   {
      return symbolString;
   }

   private List<HotListRow> getNewHotListRowList()
   {
      List<AccessHotList> accessHotListList = null;
      try
      {
         accessHotListList = SpringServiceFacade.retrieveAccessHotList(HotListSize);

         if (accessHotListList.size() == 0)
         {
            String[] symbols =
            { "amd", "lnkd", "aapl", "c", "ual", "cyd", "fro", "goog", "gs", "hov", "jpm", "lvs", "ms",
                  "msft", "phm", "prft", "pba", "amzn", "vale", "vlccf", "yhoo", "slb" };

            for (String sym : symbols)
            {
               LOG.debug("Inserting " + sym);
               SpringServiceFacade.updateAccessHotList(sym);
            }

            accessHotListList = SpringServiceFacade.retrieveAccessHotList(HotListSize);
         }
      }
      catch (AppException e)
      {
         LOG.error("Exception retrieveAccessHotList: ", e);
         return new LinkedList<HotListRow>();
      }

      Collections.sort(accessHotListList, accessComparator);

      List<HotListRow> list = new LinkedList<HotListRow>();

      for (int i = 0; i < accessHotListList.size() - 2; i += 3)
      {
         HotListRow hotListRow = new HotListRow();
         hotListRow.setSymbol1(accessHotListList.get(i).getSymbol());
         hotListRow.setSymbol2(accessHotListList.get(i + 1).getSymbol());
         hotListRow.setSymbol3(accessHotListList.get(i + 2).getSymbol());
         list.add(hotListRow);
      }

      return list;
   }

   public void setHotListRowList(List<HotListRow> hotListRowList)
   {
      this.hotListRowList = hotListRowList;
   }

   public List<HotListRow> getHotListRowList()
   {
      if (accessHotListCounter == 0 || hotListRowList == null || hotListRowList.size() == 0)
      {
         hotListRowList = getNewHotListRowList();
      }

      accessHotListCounter++;

      if (accessHotListCounter > HotListRefreshCounter)
      {
         accessHotListCounter = 0;
      }

      return hotListRowList;
   }

   public void setChartData(String chartData)
   {
      this.chartData = chartData;
   }

   public String getChartData()
   {
      return chartData;
   }
   
   public String toLogin()
   {
      return "Login";
   }
   
   public String toMyListEditor()
   {
      return "MyListEditor";
   }

   public void setMyListRowList(List<HotListRow> myListRowList)
   {
      this.myListRowList = myListRowList;
   }

   public List<HotListRow> getMyListRowList()
   {
      myListRowList = new LinkedList<HotListRow>();

      String stockList = getUser().getStockList();
      if (stockList != null)
      {
         String[] symbols = stockList.split(MbaUtils.SPACE);
         for (int i = 0; i < symbols.length;)
         {
            HotListRow hotListRow = new HotListRow();
            hotListRow.setSymbol1(symbols[i]);
            i++;

            if (i < symbols.length)
            {
               hotListRow.setSymbol2(symbols[i]);
            }
            i++;

            if (i < symbols.length)
            {
               hotListRow.setSymbol3(symbols[i]);
            }
            i++;

            myListRowList.add(hotListRow);
         }
      }

      return myListRowList;
   }

   public void setDividendData(String dividendData)
   {
      this.dividendData = dividendData;
   }

   public String getDividendData()
   {
      return dividendData;
   }

}
