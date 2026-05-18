package com.learn.hibernate.orm;


import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.CascadeType;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;


@Entity
@Table(name = "portfolios")
public class Portfolio
implements Serializable
{
   @Id
   @Column(name = "id")
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Integer   id = 0;

   @Column(name = "cash")
   private float cash;

   @OneToOne(mappedBy = "portfolio")
   private Customer customer;

   @OneToMany(mappedBy="portfolio", cascade = CascadeType.ALL)
   @OrderBy("symbol")
   private List<PortfolioItem>  portfolioItems;

   public List<PortfolioItem> getPortfolioItems()
   {
      return portfolioItems;
   }

   public void setId(Integer value)
   {
      this.id = value;
   }

   public Integer getId()
   {
      return id;
   }

   public Portfolio(float cash)
   {
      this.cash = cash;
   }

   public float getCash()
   {
      return cash;
   }

   public boolean contains(String symbol)
   {
      if (portfolioItems == null)
      {
         return false;
      }

      for (PortfolioItem pi : portfolioItems)
      {
         if (pi.getSymbol().equalsIgnoreCase(symbol))
         {
            return true;
         }
      }

      return false;
   }

   public int getNumberOfShares(String symbol)
   {
      PortfolioItem pi = getPortfolioItem(symbol);

      if (pi != null)
      {
         return pi.getShares();
      }

      return 0;
   }

   public PortfolioItem getPortfolioItem(String symbol)
   {
      if (contains(symbol))
      {
         for (PortfolioItem pi : portfolioItems)
         {
            if (pi.getSymbol().equalsIgnoreCase(symbol))
            {
               return pi;
            }
         }
      }

      return null;
   }

   public void buyStock(String symbol, int sharesBought, float purchasePrice)
   {
      cash -= sharesBought * purchasePrice;

      PortfolioItem pi = getPortfolioItem(symbol);

      if (pi != null)
      {
         pi.setShares(pi.getShares() + sharesBought);
      }
      else
      {
         pi = new PortfolioItem();
         pi.setSymbol(symbol);
         pi.setShares(sharesBought);
         portfolioItems.add(pi);
      }
   }

   public void sellStock(String symbol, int sharesSold, float sellPrice)
   {
      cash += sharesSold * sellPrice;

      PortfolioItem pi = getPortfolioItem(symbol);

      int currentShares = pi.getShares();
      int sharesLeft = currentShares - sharesSold;
      if (sharesLeft == 0)
      {
         portfolioItems.remove(pi);
      }
      else
      {
         pi.setShares(sharesLeft);
      }
   }

   public boolean canBuy(int shares, float purchasePrice)
   {
      if ((shares * purchasePrice) <= cash)
      {
         return true;
      }
      else
      {
         return false;
      }
   }

   public void setCustomer(Customer value)
   {
      this.customer = value;
   }

   public Customer getCustomer()
   {
      return customer;
   }
}

