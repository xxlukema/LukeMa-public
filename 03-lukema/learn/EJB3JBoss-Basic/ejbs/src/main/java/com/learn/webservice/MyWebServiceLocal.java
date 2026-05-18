package com.learn.webservice;


import javax.ejb.Local;


@Local
public interface MyWebServiceLocal
{
   public String test(Integer input);
}
