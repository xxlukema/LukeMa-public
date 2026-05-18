package com.learn.session;


import java.io.Serializable;

import javax.ejb.Remote;


@Remote
public interface LukeProcSessionBeanRemote
   extends Serializable
{
   public void execute()
      throws Exception;

}
