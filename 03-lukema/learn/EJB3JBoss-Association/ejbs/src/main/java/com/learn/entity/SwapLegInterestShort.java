package com.learn.entity;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("4")
public class SwapLegInterestShort
   extends SwapLeg
{
   private static final long serialVersionUID = 1L;

   private int               noExist;

   public SwapLegInterestShort()
   {
      super(StructureType.INTEREST, LongShort.SHORT);
   }

   @Override
   public SwapLegInterestLong getLinkedLeg()
   {
      return this.getParent().getSwapLegInterestLong();
   }

   public void setNoExist(int noExist)
   {
      this.noExist = noExist;
   }

   public int getNoExist()
   {
      return noExist;
   }
}
