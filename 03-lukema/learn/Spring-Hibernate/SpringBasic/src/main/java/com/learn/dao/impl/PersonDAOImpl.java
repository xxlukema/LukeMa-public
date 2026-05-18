package com.learn.dao.impl;


import java.util.List;

import org.springframework.stereotype.Repository;

import com.learn.bean.Person;
import com.learn.dao.PersonDAO;


@Repository("personDAO")
public class PersonDAOImpl
    extends CommonDAOImpl
    implements PersonDAO {
    public List<Person> list()
        throws Exception {
        return super.list(Person.class);
    }
}
