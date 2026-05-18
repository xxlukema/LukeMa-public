package com.learn.mockito.test.util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import com.learn.mockito.bean.Person;
import com.learn.mockito.dao.PersonDao;
import com.learn.mockito.service.PersonService;


public class PersonServiceTestUtils {

    private static final Logger LOG = LogManager.getLogger(PersonServiceTestUtils.class);

    public static void testFoundThenUpdate(PersonDao personDao, PersonService personService) {

        LOG.info("Begin Test.");

        Person david = new Person(2, "David");

        Mockito.when(personDao.find(1)).thenReturn(david);
        Person personFound = personService.find(1);
        Assert.assertNotNull("Found person", personFound);
        Assert.assertEquals(david.getPersonName(), personFound.getPersonName());

        boolean updated = personService.update(personFound);
        Assert.assertTrue(updated);
        Mockito.verify(personDao, Mockito.times(1)).find(1);

        ArgumentCaptor<Person> personCaptor = ArgumentCaptor.forClass(Person.class);
        Mockito.verify(personDao).update(personCaptor.capture());
        Person updatedPerson = personCaptor.getValue();
        Assert.assertEquals(personFound.getPersonName(), updatedPerson.getPersonName());
        // asserts that during the test, there are no other calls to the mock
        // object.
        Mockito.verifyNoMoreInteractions(personDao);

        LOG.info("End Test.");
    }

    public static void testNotFoundNoUpdate(PersonDao personDao, PersonService personService) {

        LOG.info("Begin Test.");

        Person person = new Person(1, "Phillip");

        Mockito.when(personDao.find(1)).thenReturn(null);
        person = personService.find(1);

        boolean updated = personService.update(person);
        Assert.assertFalse(updated);
        Mockito.verify(personDao).find(1);
        Mockito.verifyZeroInteractions(personDao);
        Mockito.verifyNoMoreInteractions(personDao);

        LOG.info("End Test.");
    }
}
