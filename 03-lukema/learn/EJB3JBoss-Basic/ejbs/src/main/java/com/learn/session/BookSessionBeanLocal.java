package com.learn.session;


import java.io.Serializable;

import javax.ejb.Local;


@Local
public interface BookSessionBeanLocal
   extends Serializable
{
   public void testCommit()
      throws Exception;

   public void testRollback()
      throws Exception;
}
