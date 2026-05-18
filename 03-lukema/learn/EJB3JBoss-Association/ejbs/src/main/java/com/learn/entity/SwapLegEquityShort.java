package com.learn.entity;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("2")
public class SwapLegEquityShort
   extends SwapLeg
{
   private static final long serialVersionUID = 1L;

   public SwapLegEquityShort()
   {
      super(StructureType.EQUITY, LongShort.SHORT);
   }

   @Override
   public SwapLegEquityLong getLinkedLeg()
   {
      return this.getParent().getSwapLegEquityLong();
   }

}
