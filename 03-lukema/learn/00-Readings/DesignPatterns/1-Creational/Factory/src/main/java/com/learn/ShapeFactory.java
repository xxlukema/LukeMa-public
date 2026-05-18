package com.learn;

public class ShapeFactory {
	 public static Shape createShape(ShapeType shapeType)
	   {
	      switch (shapeType)
	      {
	         case Circle:
	            return new CircleShape();
	         case Square:
	            return new SquareShape();
	         case Triangle:
	            return new TriangleShape();
	         default:
	            return null;
	      }
	   }

}
