package com.learn.common.domain;


import java.io.Serializable;


public class DataRequest
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   private String            symbol;

   public String getSymbol()
   {
      return symbol;
   }

   public void setSymbol(String symbol)
   {
      this.symbol = symbol;
   }

}
