package com.learn.mockito.service.impl;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.inject.Inject;
import com.learn.mockito.bean.Person;
import com.learn.mockito.dao.PersonDao;
import com.learn.mockito.service.PersonService;


public class PersonServiceImpl
    implements PersonService {

    private static final Logger LOG = LogManager.getLogger(PersonServiceImpl.class);

    private final PersonDao personDao;

    @Inject
    public PersonServiceImpl(PersonDao personDao) {
        this.personDao = personDao;
    }

    @Override
    public Person find(Integer personID) {

        LOG.info("PersonServiceImpl.find() called.");

        Person person = personDao.find(personID);
        return person;
    }

    @Override
    public boolean update(Person person) {

        LOG.info("PersonServiceImpl.update() called.");

        if (person != null) {
            personDao.update(person);
            return true;
        } else {
            return false;
        }
    }
}
