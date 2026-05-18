package com.learn.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.learn.bean.Person;
import com.learn.dao.PersonDAO;
import com.learn.service.PersonService;


@Service("personService")
public class PersonServiceImpl
    extends CommonServiceImpl
    implements PersonService {

    @Autowired
    @Qualifier("personDAO")
    private PersonDAO personDAO;

    public List<Person> list()
        throws Exception {
        return getPersonDAO().list();
    }

    public void setPersonDAO(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public PersonDAO getPersonDAO() {
        return personDAO;
    }

}
