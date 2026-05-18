package com.learn.jta;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.learn.bean.Address;


public class AddressJTAImpl
   extends JdbcDaoSupport
   implements AddressJTA
{

   public List<Address> list()
      throws Exception
   {
      Integer id = 0;
      Object[] args = { id };
      int[] argTypes = { java.sql.Types.INTEGER };

      JdbcTemplate jdbcTemplate = getJdbcTemplate();

      @SuppressWarnings("unchecked")
      List<Address> addresses = jdbcTemplate.query(SQL, args, argTypes, new AddressRowMapper());

      return addresses;
   }

   class AddressRowMapper
      implements RowMapper
   {
      public Object mapRow(ResultSet rs, int index)
         throws SQLException
      {
         Address address = new Address();
         address.setAddressId(rs.getInt(1));
         address.setProvince(rs.getString(2));
         address.setCountry(rs.getString(3));
         address.setPostcode(rs.getString(4));
         address.setStreet(rs.getString(5));
         address.setCity(rs.getString(6));

         return address;
      }
   }
}
