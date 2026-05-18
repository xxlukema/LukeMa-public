package com.learn.order;


import com.learn.burger.Burger;
import com.learn.drink.Drink;


public class FastFoodOrder
{
   private Burger burger = null;

   private Drink  drink  = null;

   public Burger getBurger()
   {
      return burger;
   }

   public void setBurger(Burger burger)
   {
      this.burger = burger;
   }

   public Drink getDrink()
   {
      return drink;
   }

   public void setDrink(Drink drink)
   {
      this.drink = drink;
   }
   
   public void printOrder()
   {
      burger.printBurger();
      drink.printDrink();
   }
   
   public float getPrice()
   {
      return burger.getPrice() + drink.getPrice();
   }
}
