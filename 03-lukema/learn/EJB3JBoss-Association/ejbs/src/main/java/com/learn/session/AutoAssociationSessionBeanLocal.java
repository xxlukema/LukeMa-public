package com.learn.session;


import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface AutoAssociationSessionBeanLocal
   extends Serializable
{
   public void addRecord()
      throws Exception;

   public void retrieveData()
      throws Exception;
}
