package com.learn.jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.learn.bean.Address;


public class AddressJDBCImpl
   implements AddressJDBC
{
   private DataSource dataSource;

   public void setDataSource(DataSource dataSource)
   {
      this.dataSource = dataSource;
   }

   public List<Address> list()
      throws Exception
   {
      List<Address> addresses = new ArrayList<Address>();

      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet rs = null;
      try
      {
         conn = dataSource.getConnection();
         ps = conn.prepareStatement(SQL);

         Long id = 0L;
         ps.setLong(1, id);
         rs = ps.executeQuery();

         while (rs.next())
         {
            Address address = new Address();
            address.setAddressId(rs.getInt(1));
            address.setProvince(rs.getString(2));
            address.setCountry(rs.getString(3));
            address.setPostcode(rs.getString(4));
            address.setStreet(rs.getString(5));
            address.setCity(rs.getString(6));

            addresses.add(address);
         }
      }
      finally
      {
         if (rs != null)
         {
            rs.close();
         }

         if (ps != null)
         {
            ps.close();
         }

         if (conn != null)
         {
            conn.close();
         }
      }

      return addresses;
   }
}
