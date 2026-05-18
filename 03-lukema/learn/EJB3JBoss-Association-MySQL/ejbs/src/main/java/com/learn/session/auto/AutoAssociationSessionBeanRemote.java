package com.learn.session.auto;


import java.io.Serializable;

import javax.ejb.Remote;


@Remote
public interface AutoAssociationSessionBeanRemote
   extends Serializable
{
   public void addRecord()
      throws Exception;

   public void retrieveData()
      throws Exception;
}
