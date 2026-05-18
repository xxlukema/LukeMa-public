package com.learn.session;


import java.io.Serializable;

import javax.ejb.Local;

import com.learn.entity.BasketEvent;


@Local
public interface BasketEventSessionBeanLocal
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
