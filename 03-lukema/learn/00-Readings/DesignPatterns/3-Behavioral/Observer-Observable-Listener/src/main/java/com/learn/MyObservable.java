package com.learn;


import java.util.Observable;


public class MyObservable
   extends Observable
{
   public void setReady()
   {
      setChanged();
   }
}
