package com.learn.hibernate.orm;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;

import net.neurotech.quotes.Quote;


@Entity
@Table(name = "portfolio_items")
public class PortfolioItem
implements Serializable
{
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Integer   id = 0;

   @Column(name = "symbol", length = 8)
   private String symbol;

   @Column(name = "shares")
   private int    shares;

   @ManyToOne
   @JoinColumn(name="portfolio_fk")
   private Portfolio portfolio;

   public Portfolio getPortfolio()
   {
      return portfolio;
   }

   private Quote  quote;

   private double currentValue;

   private double gainLoss;

   public void setId(Integer value)
   {
      this.id = value;
   }

   public Integer getId()
   {
      return id;
   }

   public int getShares()
   {
      return shares;
   }

   public void setShares(int value)
   {
      this.shares = value;
   }

   public String getSymbol()
   {
      return symbol;
   }

   public void setSymbol(String value)
   {
      this.symbol = value;
   }

   public double getCurrentValue()
   {
      return currentValue;
   }

   public void setCurrentValue(double value)
   {
      this.currentValue = value;
   }

   public Quote getQuote()
   {
      return quote;
   }

   public void setQuote(Quote value)
   {
      this.quote = value;
   }

   public double getGainLoss()
   {
      return gainLoss;
   }

   public void setGainLoss(double value)
   {
      this.gainLoss = value;
   }
}

