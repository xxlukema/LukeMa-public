package com.learn.jsf.controller.push;


import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.neurotech.quotes.Quote;

import org.apache.log4j.Logger;

import com.learn.jsf.util.JavaFinancialLibraryUtils;


abstract public class BaseQuoteWatcher
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   protected static final Logger LOG = Logger.getLogger(BaseQuoteWatcher.class);

   public static final String INDEX_PUSH_GROUP = "IndexPushGroup";

   private static final TimeZone TZ = TimeZone.getTimeZone("America/Chicago");
   protected static final String ValueFormat = "#,##0.00";
   protected static final String ChangeFormat = "#,##0.00";
   protected static final String PctChangeFormat = "#,##0.00%";
   private String value;
   private String change;
   private String pctChange;
   private boolean up;
   private Quote quote = null;
   private long lastUpdatedMiliseconds = 0;
   private final int UpdateIntervalMiliseconds = 20 * 1000;
   private static Lock lock = new ReentrantLock();

   @PostConstruct
   public void init()
   {
    //  PushRenderer.addCurrentSession(INDEX_PUSH_GROUP);
   }

   @PreDestroy
   protected void destroy()
   {
    //  PushRenderer.removeCurrentSession(INDEX_PUSH_GROUP);

      if (!JavaFinancialLibraryUtils.QuoteThreadPoolExecutor.isShutdown())
      {
         JavaFinancialLibraryUtils.QuoteThreadPoolExecutor.shutdownNow();
      }
   }

   private void checkForQuoteUpdate()
   {
      boolean locked = false;

      try
      {
         locked = lock.tryLock(500, TimeUnit.MILLISECONDS);
      }
      catch (InterruptedException e)
      {
      }

      if (locked)
      {
         try
         {
            long currentTimeMiliseconds = System.currentTimeMillis();

            if (currentTimeMiliseconds - lastUpdatedMiliseconds > UpdateIntervalMiliseconds)
            {
               updateQuote();

               lastUpdatedMiliseconds = currentTimeMiliseconds;
            }
         }
         finally
         {
            lock.unlock();
         }
      }
   }

   private void updateQuote()
   {
      Quote newQuote = JavaFinancialLibraryUtils.getQuoteWait(getSymbol());
      if (newQuote != null)
      {
         quote = newQuote;
      }
   }

   abstract public String getSymbol();

   public void setValue(String value)
   {
      this.value = value;
   }

   public String getValue()
   {
      checkForQuoteUpdate();

      if (quote == null)
      {
         return null;
      }

      try
      {
         value = new DecimalFormat(ValueFormat).format(quote.getValue());
      }
      catch (Throwable t)
      {
         LOG.error("quote.getChange(): " + quote.getChange(), t);
      }

      return value;
   }

   public void setChange(String change)
   {
      this.change = change;
   }

   public String getChange()
   {
      if (quote == null)
      {
         return null;
      }

      try
      {
         change = new DecimalFormat(ValueFormat).format(quote.getChange());
      }
      catch (Throwable t)
      {
         LOG.error("quote.getChange(): " + quote.getChange(), t);
      }

      return change;
   }

   public void setUp(boolean up)
   {
      this.up = up;
   }

   public boolean isUp()
   {
      if (quote == null)
      {
         return false;
      }

      try
      {
         up = (quote.getChange() >= 0);
      }
      catch (Throwable t)
      {
         LOG.error("quote.getChange(): " + quote.getChange(), t);
      }

      return up;
   }

   public void setQuote(Quote quote)
   {
      this.quote = quote;
   }

   public Quote getQuote()
   {
      return quote;
   }

   public String getPctChange()
   {
      if (quote == null)
      {
         return null;
      }

      try
      {
         pctChange = new DecimalFormat(PctChangeFormat).format(quote.getPctChange() / 100.0);
      }
      catch (Throwable t)
      {
         LOG.error("quote.getChange(): " + quote.getChange(), t);
      }

      return pctChange;
   }

   public void setPctChange(String pctChange)
   {
      this.pctChange = pctChange;
   }

   public boolean isTradingTime()
   {
      GregorianCalendar gregorianCalendar = new GregorianCalendar(TZ);
      int dayOfWeek = gregorianCalendar.get(Calendar.DAY_OF_WEEK);
      if (dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY)
      {
         int hour = gregorianCalendar.get(Calendar.HOUR_OF_DAY);
         int minute = gregorianCalendar.get(Calendar.MINUTE);

         if ((hour == 8 && minute > 30) || (hour > 8 && hour < 15))
         {
            return true;
         }
      }

      return false;
   }

}
