package com.learn.session;


import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface LukeProcSessionBeanLocal
   extends Serializable
{
   public void execute()
      throws Exception;

}
