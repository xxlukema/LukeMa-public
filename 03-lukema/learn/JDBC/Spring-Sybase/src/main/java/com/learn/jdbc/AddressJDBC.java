package com.learn.jdbc;


import java.util.List;

import com.learn.bean.Address;
import com.learn.jta.AddressJTA;


public interface AddressJDBC
{
   public static final String SQL = AddressJTA.SQL;

   public List<Address> list()
      throws Exception;
}
