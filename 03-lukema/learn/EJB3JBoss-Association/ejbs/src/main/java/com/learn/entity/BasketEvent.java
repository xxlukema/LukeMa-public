package com.learn.entity;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "BasketEvent")
@TableGenerator(name = "BasketEventIdGenerator", table = "idobj_gen_view", pkColumnName = "type", pkColumnValue = "2054", valueColumnName = "avail_id", allocationSize = 1, initialValue = 1)
public class BasketEvent
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   @Id
   @GeneratedValue(strategy = GenerationType.TABLE, generator = "SwapIdGenerator")
   @Column(name = "eventId")
   private Long              id;

   private Long              legId;

   private Long              instrId;

   @Temporal(TemporalType.DATE)
   private Date              tradeDate;

   @Temporal(TemporalType.DATE)
   private Date              settleDate;

   private Float             qty;

   private Float             basePrice;

   private Float             price;

   private Float             fxRate           = 1.0F;

   private Float             intRate;

   private Short             eventType;

   private Long              enterUser;

   @Temporal(TemporalType.TIMESTAMP)
   private Date              enterTime;

   public Long getId()
   {
      return id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public Long getLegId()
   {
      return legId;
   }

   public void setLegId(Long legId)
   {
      this.legId = legId;
   }

   public Long getInstrId()
   {
      return instrId;
   }

   public void setInstrId(Long instrId)
   {
      this.instrId = instrId;
   }

   public Date getTradeDate()
   {
      return tradeDate;
   }

   public void setTradeDate(Date tradeDate)
   {
      this.tradeDate = tradeDate;
   }

   public Date getSettleDate()
   {
      return settleDate;
   }

   public void setSettleDate(Date settleDate)
   {
      this.settleDate = settleDate;
   }

   public Float getQty()
   {
      return qty;
   }

   public void setQty(Float qty)
   {
      this.qty = qty;
   }

   public Float getPrice()
   {
      price = getBasePrice() * getFxRate();
      return price;
   }

   public void setPrice(Float price)
   {
      setBasePrice(price / getFxRate());
      this.price = price;
   }

   public Float getBasePrice()
   {
      return basePrice;
   }

   public void setBasePrice(Float basePrice)
   {
      this.basePrice = basePrice;
      price = getBasePrice() * getFxRate();
   }

   public Float getFxRate()
   {
      return fxRate;
   }

   public void setFxRate(Float fxRate)
   {
      this.fxRate = fxRate;
   }

   public Float getIntRate()
   {
      return intRate;
   }

   public void setIntRate(Float intRate)
   {
      this.intRate = intRate;
   }

   public Short getEventType()
   {
      return eventType;
   }

   public void setEventType(Short eventType)
   {
      this.eventType = eventType;
   }

   public Long getEnterUser()
   {
      return enterUser;
   }

   public void setEnterUser(Long enterUser)
   {
      this.enterUser = enterUser;
   }

   public Date getEnterTime()
   {
      return enterTime;
   }

   public void setEnterTime(Date enterTime)
   {
      this.enterTime = enterTime;
   }

}
