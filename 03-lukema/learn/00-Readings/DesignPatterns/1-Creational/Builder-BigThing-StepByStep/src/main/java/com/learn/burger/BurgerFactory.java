package com.learn.burger;

public class BurgerFactory {
	 public static Burger createBurger(BurgerType burgerType)
	   {
	      switch (burgerType)
	      {
	         case CheeseBugerType:
	            return new CheeseBurger();
	         case BigMacType:
	            return new BigMac();
	         case BigJackType:
	            return new BigJack();
	         default:
	            return null;
	      }
	   }

}
