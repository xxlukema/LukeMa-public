package com.learn;


import org.apache.log4j.Logger;

import com.learn.burger.BurgerType;
import com.learn.drink.DrinkType;
import com.learn.order.FastFoodOrder;
import com.learn.order.FastFoodOrderBuilder;


public class HelloWorld
{
   private static final Logger LOG = Logger.getLogger(HelloWorld.class);

   public static void main(String[] args)
   {
      LOG.info("Hello World!");

      FastFoodOrderBuilder fastFoodOrderBuilder = new FastFoodOrderBuilder();
      FastFoodOrder fastFoodOrder = fastFoodOrderBuilder.buildOrder(BurgerType.BigMacType, DrinkType.OrangeJuiceType);
      fastFoodOrderBuilder.completeOrder(fastFoodOrder);
   }
}
