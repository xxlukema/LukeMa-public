package com.learn.entity;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("1")
public class SwapLegEquityLong
   extends SwapLeg
{
   private static final long serialVersionUID = 1L;

   public SwapLegEquityLong()
   {
      super(StructureType.EQUITY, LongShort.LONG);
   }

   @Override
   public SwapLegEquityShort getLinkedLeg()
   {
      return this.getParent().getSwapLegEquityShort();
   }

}
