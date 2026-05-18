package com.learn.session;


import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.learn.entity.BasketEvent;


@Stateless
public class BasketEventSessionBean
   implements BasketEventSessionBeanLocal, BasketEventSessionBeanRemote
{
   private static final long     serialVersionUID              = 1L;

   protected static final Logger LOG                           = Logger
                                                                     .getLogger(BasketEventSessionBean.class);

   private final String          oqlBasketEventByLegId_InstrId = "from BasketEvent where legId = ? and instrId = ?";

   private static final Long     LegId                         = 998877L;

   private static final Long     InstrId                       = 998877L;

   @PersistenceContext(unitName = "entity-persistence-unit")
   private EntityManager         entityManager;

   @Override
   public void addRecord()
      throws Exception
   {
      LOG.info("########## addRecord() invoked.");

      BasketEvent basketEvent = retrieveDataProtected();
      if (basketEvent == null)
      {
         basketEvent = new BasketEvent();

         basketEvent.setEnterUser(0L);
         basketEvent.setEventType((short) 0);
         basketEvent.setIntRate(0.0F);
         basketEvent.setFxRate(2.0F);
         basketEvent.setBasePrice(10.0F);
         basketEvent.setLegId(LegId);
         basketEvent.setInstrId(InstrId);
         basketEvent.setQty(500.0F);

         basketEvent.setTradeDate(new Date());
         basketEvent.setSettleDate(new Date());
         basketEvent.setEnterTime(new Date());

         entityManager.merge(basketEvent);

         LOG.info("Data Initiated.");
      }
      else
      {
         LOG.info("Data already exists.");
      }

   }

   @Override
   public BasketEvent retrieveData()
      throws Exception
   {
      LOG.info("########## retrieveData() invoked.");

      BasketEvent basketEvent = retrieveDataProtected();
      if (basketEvent != null)
      {
         LOG.info("BasketEvent Id = " + basketEvent.getId());
      }
      else
      {
         LOG.info("BasketEvent not found: LegId = " + LegId + " InstrId = " + InstrId);
      }

      LOG.info("########## retrieveData() completed.");

      return basketEvent;
   }

   protected BasketEvent retrieveDataProtected()
      throws Exception
   {
      Query query = entityManager.createQuery(oqlBasketEventByLegId_InstrId);
      query.setParameter(1, LegId);
      query.setParameter(2, InstrId);

      @SuppressWarnings("unchecked")
      List<BasketEvent> basketEvents = query.getResultList();

      LOG.info("BasketEvents.size() = " + basketEvents.size());

      BasketEvent basketEvent = null;
      if (basketEvents.size() > 0)
      {
         basketEvent = basketEvents.get(0);
      }

      return basketEvent;
   }

   @Override
   public void updateRecord()
      throws Exception
   {
      LOG.info("########## updateRecord() invoked.");

      BasketEvent basketEvent = retrieveDataProtected();

      if (basketEvent != null)
      {
         basketEvent.setQty(basketEvent.getQty() + 111);
         BasketEvent basketEvent2 = entityManager.merge(basketEvent);
         LOG.info("########## updateRecord() completed. Qty updated from " + basketEvent.getQty() + " to "
               + basketEvent2.getQty());
      }

      LOG.info("########## updateRecord() completed.");
   }

   @Override
   public void removeRecord()
      throws Exception
   {
      LOG.info("########## removeRecord() invoked.");

      BasketEvent basketEvent = retrieveDataProtected();
      if (basketEvent != null)
      {
         entityManager.remove(basketEvent);
      }

      LOG.info("########## removeRecord() completed.");
   }
}
