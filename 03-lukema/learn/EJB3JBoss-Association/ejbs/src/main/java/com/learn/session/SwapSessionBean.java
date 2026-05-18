package com.learn.session;


import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.learn.entity.Swap;
import com.learn.entity.SwapLeg;
import com.learn.entity.SwapLegEquityLong;
import com.learn.entity.SwapLegEquityShort;
import com.learn.entity.SwapLegInterestLong;
import com.learn.entity.SwapLegInterestShort;
import com.learn.exception.AppException;


@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class SwapSessionBean
   implements SwapSessionBeanLocal, SwapSessionBeanRemote
{
   private static final long     serialVersionUID = 1L;

   protected static final Logger LOG              = Logger.getLogger(SwapSessionBean.class);

   // private final String          oqlSwapBySwapLegId = "select swap from Swap swap join swap.swapLegs swapLeg where swapLeg.fiId = ?";

   private final String          oqlSwapBySwapNum = "from Swap where swapNum = ?";

   private static final int      SwapNum          = 998877;

   @PersistenceContext(unitName = "entity-persistence-unit")
   private EntityManager         entityManager;

   @Override
   @TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
   public void addRecord()
      throws AppException, Exception
   {
      LOG.info("########## addRecord() invoked.");

      Swap swap = retrieveDataProtected();
      if (swap == null)
      {
         swap = new Swap();
         swap.setEndDate(new Date());
         swap.setEnterTime(new Date());
         swap.setLastActivityTime(new Date());
         swap.setName("name");
         swap.setStartDate(new Date());
         swap.setTradeDate(new Date());
         swap.setSwapNum(SwapNum);

         swap = entityManager.merge(swap);

         SwapLeg swapLeg1 = new SwapLegEquityLong();
         swapLeg1.setEnterTime(new Date());
         swapLeg1.setFxResetSource("fxRestSrc1EL");
         swapLeg1.setLastActivityTime(new Date());
         swapLeg1.setResetSource("resetSrc1EL");
         swapLeg1.setParent(swap);

         SwapLeg swapLeg2 = new SwapLegEquityShort();
         swapLeg2.setEnterTime(new Date());
         swapLeg2.setFxResetSource("fxRestSrc2ES");
         swapLeg2.setLastActivityTime(new Date());
         swapLeg2.setResetSource("resetSrc2ES");
         swapLeg2.setParent(swap);

         SwapLeg swapLeg3 = new SwapLegInterestLong();
         swapLeg3.setEnterTime(new Date());
         swapLeg3.setFxResetSource("fxRestSrc3IL");
         swapLeg3.setLastActivityTime(new Date());
         swapLeg3.setResetSource("resetSrc3IL");
         swapLeg3.setParent(swap);

         SwapLeg swapLeg4 = new SwapLegInterestShort();
         swapLeg4.setEnterTime(new Date());
         swapLeg4.setFxResetSource("fxRestSrc4IS");
         swapLeg4.setLastActivityTime(new Date());
         swapLeg4.setResetSource("resetSrc4IS");
         swapLeg4.setParent(swap);

         entityManager.merge(swapLeg1);
         entityManager.merge(swapLeg2);
         entityManager.merge(swapLeg3);
         entityManager.merge(swapLeg4);

         String msg = "Data Initiated. "
               + "This is executed in memory first before commit. This will be reached even if entityManager "
               + "throws exception, because entityManager is executed "
               + "in batch at the end of the new session.";

         LOG.info("########## " + msg);
      }
      else
      {
         LOG.info("Data Swap alread exists: swapNum = " + SwapNum);
      }
   }

   @Override
   public Swap retrieveData()
      throws Exception
   {
      LOG.info("########## retrieveData() invoked.");

      Swap swap = retrieveDataProtected();
      if (swap != null)
      {
         LOG.info("Swap Id = " + swap.getId());
         LOG.info("Swap Name = " + swap.getName());

         SwapLeg swapLeg = swap.getSwapLegEquityLong();
         LOG.info(swapLeg.getId() + " " + swapLeg.getLegId() + " " + swapLeg.getResetSource() + " "
               + swapLeg.getFxResetSource() + " " + swapLeg.getLinkedLeg().getId() + " "
               + swapLeg.getStructureType() + " " + swapLeg.getLongShort());

         swapLeg = swap.getSwapLegEquityShort();
         LOG.info(swapLeg.getId() + " " + swapLeg.getLegId() + " " + swapLeg.getResetSource() + " "
               + swapLeg.getFxResetSource() + " " + swapLeg.getLinkedLeg().getId() + " "
               + swapLeg.getStructureType() + " " + swapLeg.getLongShort());

         swapLeg = swap.getSwapLegInterestLong();
         LOG.info(swapLeg.getId() + " " + swapLeg.getLegId() + " " + swapLeg.getResetSource() + " "
               + swapLeg.getFxResetSource() + " " + swapLeg.getLinkedLeg().getId() + " "
               + swapLeg.getStructureType() + " " + swapLeg.getLongShort());

         swapLeg = swap.getSwapLegInterestShort();
         LOG.info(swapLeg.getId() + " " + swapLeg.getLegId() + " " + swapLeg.getResetSource() + " "
               + swapLeg.getFxResetSource() + " " + swapLeg.getLinkedLeg().getId() + " "
               + swapLeg.getStructureType() + " " + swapLeg.getLongShort());
      }
      else
      {
         LOG.info("Swap not found for swapNum = " + SwapNum);
      }

      LOG.info("########## retrieveData() completed.");

      return swap;
   }

   protected Swap retrieveDataProtected()
      throws Exception
   {
      Query query = entityManager.createQuery(oqlSwapBySwapNum);
      query.setParameter(1, SwapNum);

      @SuppressWarnings("unchecked")
      List<Swap> swaps = query.getResultList();

      Swap swap = null;
      if (swaps.size() > 0)
      {
         swap = swaps.get(0);
      }

      return swap;
   }

   @Override
   public void removeData()
      throws Exception
   {
      LOG.info("########## removeData() invoked.");

      Swap swap = retrieveDataProtected();
      if (swap != null)
      {
         entityManager.remove(swap);
      }

      LOG.info("########## removeData() completed.");
   }
}
