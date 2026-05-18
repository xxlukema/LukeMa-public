package com.learn.mockito.dao;


import com.google.inject.ImplementedBy;
import com.learn.mockito.bean.Person;
import com.learn.mockito.dao.impl.PersonDaoImpl;


@ImplementedBy(PersonDaoImpl.class)
public interface PersonDao {
    
    public Person find(Integer personID);
    public void update(Person person);
}
