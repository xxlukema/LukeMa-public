package com.learn.repository;


import com.learn.pojo.CurrentDatePojo;


public interface CommonRepository {

    /**
     * @Query("SELECT p FROM Person p WHERE LOWER(p.lastName) = LOWER(:lastName)")
     * public List<Person> find(@Param("lastName") String lastName);
     *
     * @Query(value = "SELECT current_date as date", nativeQuery = true)
     *
     * */
    public CurrentDatePojo selectCurrentDate();

}
