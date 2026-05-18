/* package com.learn.batch;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;


public class PersonItemProcessor
    implements ItemProcessor<PersonIn, PersonOut> {

    private static final Logger log = LogManager.getLogger();

    @Override
    public PersonOut process(@NonNull final PersonIn person)
        throws Exception {

        final String firstname = person.getFirstname().toUpperCase();
        final String lastname = person.getLastname().toUpperCase();

        final PersonOut transformedPerson = new PersonOut(0, firstname, lastname);

        log.info("Converting (" + person + ") into (" + transformedPerson + ")");

        return transformedPerson;
    }

}
 */
