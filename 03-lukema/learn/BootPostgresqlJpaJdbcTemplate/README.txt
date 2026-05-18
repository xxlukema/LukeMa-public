
1. NoRowMapperNamedParameterJdbcTemplateDaoImpl approach is easier because it does not need to implement RowMapper. 
   Users rename the "select fname as first_name" or "select fname as firstName" columns to automatically map the column to pojo property.
   
   This is the most preferred.

2. NamedParameterJdbcTemplateDaoImpl approach needs to implement RowMapper interface.

   This is less preferred.

3. JPA JpaNamedQueryDaoImpl approach uses annotations inside @MappedSuperclass to map query column name into pojo property.
   However, the query parameters must be re-matched from map to queryParameters:
        // @formatter:off
        query.setParameter("dat", namedParameters.get("dat"))
             .setParameter("num", namedParameters.get("num"));
        // @formatter:on
    
   This is the least preferred.

4. If JPA EntityManager is used together with insert and update, JpaNamedQueryDaoImpl should be used because EntityManager 
   can use cached data. 


