package com.learn.mockito.service;


import com.google.inject.ImplementedBy;
import com.learn.mockito.bean.Person;
import com.learn.mockito.service.impl.PersonServiceImpl;


@ImplementedBy(PersonServiceImpl.class)
public interface PersonService {

    public abstract Person find(Integer personID);

    public abstract boolean update(Person person);

}
