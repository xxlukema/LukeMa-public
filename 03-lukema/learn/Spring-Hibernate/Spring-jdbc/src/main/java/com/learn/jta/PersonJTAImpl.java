package com.learn.jta;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.learn.bean.Person;


public class PersonJTAImpl
   extends JdbcDaoSupport
   implements PersonJTA
{

   public List<Person> list()
      throws Exception
   {
      Long id = 0L;
      Object[] args = { id };
      int[] argTypes = { java.sql.Types.NUMERIC };

      JdbcTemplate jdbcTemplate = getJdbcTemplate();

      @SuppressWarnings("unchecked")
      List<Person> people = jdbcTemplate.query(SQL, args, argTypes, new PersonRowMapper());

      return people;
   }

   class PersonRowMapper
      implements RowMapper
   {
      public Object mapRow(ResultSet rs, int index)
         throws SQLException
      {
         Person person = new Person();
         person.setId(rs.getLong(1));
         person.setName(rs.getString(2));
         person.setWeight(rs.getInt(3));

         return person;
      }
   }
}
