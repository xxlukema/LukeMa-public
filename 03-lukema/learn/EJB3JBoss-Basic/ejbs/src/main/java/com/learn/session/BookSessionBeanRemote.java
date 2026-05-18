package com.learn.session;


import java.io.Serializable;

import javax.ejb.Remote;


@Remote
public interface BookSessionBeanRemote
   extends Serializable
{
   public void testCommit()
      throws Exception;

   public void testRollback()
      throws Exception;
}
