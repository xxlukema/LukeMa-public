package com.learn.drink;

public class DrinkFactory {
	 public static Drink createDrink(DrinkType drinkType)
	   {
	      switch (drinkType)
	      {
	         case WaterType:
	            return new Water();
	         case CokeType:
	            return new Coke();
	         case OrangeJuiceType:
	            return new OrangeJuice();
	         default:
	            return null;
	      }
	   }

}
