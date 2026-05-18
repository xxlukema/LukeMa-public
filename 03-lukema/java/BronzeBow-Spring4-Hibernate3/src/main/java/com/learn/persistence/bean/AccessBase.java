package com.learn.persistence.bean;


public class AccessBase
   extends BeanBase
{
   private static final long serialVersionUID = 1L;

   private String            remoteAddress;

   private String            symbol;

   public String getRemoteAddress()
   {
      return remoteAddress;
   }

   public void setRemoteAddress(String remoteAddress)
   {
      this.remoteAddress = remoteAddress;
   }

   public String getSymbol()
   {
      return symbol;
   }

   public void setSymbol(String symbol)
   {
      this.symbol = symbol;
   }

}
