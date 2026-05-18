package com.learn.session;


import java.io.Serializable;

import javax.ejb.Remote;

import com.learn.entity.BasketEvent;


@Remote
public interface BasketEventSessionBeanRemote
   extends Serializable
{
   public void addRecord()
      throws Exception;

   public BasketEvent retrieveData()
      throws Exception;

   public void updateRecord()
      throws Exception;

   public void removeRecord()
      throws Exception;
}
