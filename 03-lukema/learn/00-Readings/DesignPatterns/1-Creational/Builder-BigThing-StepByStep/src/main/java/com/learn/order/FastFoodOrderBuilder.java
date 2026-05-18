package com.learn.order;


import org.apache.log4j.Logger;

import com.learn.burger.Burger;
import com.learn.burger.BurgerFactory;
import com.learn.burger.BurgerType;
import com.learn.drink.Drink;
import com.learn.drink.DrinkFactory;
import com.learn.drink.DrinkType;


public class FastFoodOrderBuilder
{
   private static final Logger LOG = Logger.getLogger(FastFoodOrderBuilder.class);

   public FastFoodOrder buildOrder(BurgerType burgerType, DrinkType drinkType)
   {
      Burger burger = BurgerFactory.createBurger(burgerType);
      Drink drink = DrinkFactory.createDrink(drinkType);

      FastFoodOrder fastFoodOrder = new FastFoodOrder();
      fastFoodOrder.setBurger(burger);
      fastFoodOrder.setDrink(drink);

      return fastFoodOrder;
   }

   public void completeOrder(FastFoodOrder fastFoodOrder)
   {
      fastFoodOrder.printOrder();
      
      LOG.info("Total Price: " + fastFoodOrder.getPrice());
   }
}
