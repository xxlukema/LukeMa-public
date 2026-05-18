package com.learn.batch;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;


public class PersonItemProcessor
    implements ItemProcessor<PersonIn, PersonOut> {

    private static final Logger LOG = LogManager.getLogger();

    @Override
    public PersonOut process(@NonNull final PersonIn person)
        throws Exception {

        final String firstName = person.getFirstName().toUpperCase();
        final String lastName = person.getLastName().toUpperCase();

        final PersonOut transformedPerson = new PersonOut(0, firstName, lastName);

        LOG.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}
