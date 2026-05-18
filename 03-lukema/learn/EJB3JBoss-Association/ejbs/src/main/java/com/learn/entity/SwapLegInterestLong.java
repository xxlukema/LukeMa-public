package com.learn.entity;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("3")
public class SwapLegInterestLong
   extends SwapLeg
{
   private static final long serialVersionUID = 1L;

   public SwapLegInterestLong()
   {
      super(StructureType.INTEREST, LongShort.LONG);
   }

   @Override
   public SwapLegInterestShort getLinkedLeg()
   {
      return this.getParent().getSwapLegInterestShort();
   }
}
