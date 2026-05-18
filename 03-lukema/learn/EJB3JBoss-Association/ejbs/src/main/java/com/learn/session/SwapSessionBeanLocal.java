package com.learn.session;


import java.io.Serializable;

import javax.ejb.Local;

import com.learn.entity.Swap;


@Local
public interface SwapSessionBeanLocal
   extends Serializable
{
   public void addRecord()
      throws Exception;

   public Swap retrieveData()
      throws Exception;

   public void removeData()
      throws Exception;
}
