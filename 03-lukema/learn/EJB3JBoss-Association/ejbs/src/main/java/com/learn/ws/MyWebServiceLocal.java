package com.learn.ws;


import javax.ejb.Local;


@Local
public interface MyWebServiceLocal
{
   public String test(Integer input);
}
