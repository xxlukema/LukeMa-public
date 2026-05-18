package com.learn.session;


import java.io.Serializable;

import javax.ejb.Remote;

import com.learn.entity.Swap;


@Remote
public interface SwapSessionBeanRemote
   extends Serializable
{
   public void addRecord()
      throws Exception;

   public Swap retrieveData()
      throws Exception;

   public void removeData()
      throws Exception;
}
