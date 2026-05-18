package com.learn.scalor;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class LukeProcResult
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private String            ticker;

   private Integer           swapNum;

   private Integer           swapId;

   @Temporal(value = TemporalType.DATE)
   private Date              date;

   private Float             rate;

   public String getTicker()
   {
      return ticker;
   }

   public void setTicker(String ticker)
   {
      this.ticker = ticker;
   }

   public Integer getSwapNum()
   {
      return swapNum;
   }

   public void setSwapNum(Integer swapNum)
   {
      this.swapNum = swapNum;
   }

   public Integer getSwapId()
   {
      return swapId;
   }

   public void setSwapId(Integer swapId)
   {
      this.swapId = swapId;
   }

   public Date getDate()
   {
      return date;
   }

   public void setDate(Date date)
   {
      this.date = date;
   }

   public Float getRate()
   {
      return rate;
   }

   public void setRate(Float rate)
   {
      this.rate = rate;
   }

}
