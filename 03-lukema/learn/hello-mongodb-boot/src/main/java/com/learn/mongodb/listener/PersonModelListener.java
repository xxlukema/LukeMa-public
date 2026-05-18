package com.learn.mongodb.listener;


import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

import com.learn.mongodb.model.Person;
import com.learn.mongodb.service.PersonSeqGeneratorService;

import jakarta.validation.constraints.NotNull;


@Component
public class PersonModelListener
    extends AbstractMongoEventListener<Person> {

    private PersonSeqGeneratorService personSeqGeneratorService;

    /**
     * Implicit constructor injection
     */
    public PersonModelListener(PersonSeqGeneratorService mongoSequenceGeneratorService) {
        this.personSeqGeneratorService = mongoSequenceGeneratorService;
    }

    @Override
    public void onBeforeConvert(@NotNull BeforeConvertEvent<Person> event) {
        if (event.getSource().getId() == null) {
            if (event.getSource().getId() == null || event.getSource().getId() < 1) {
                event.getSource().setId(this.personSeqGeneratorService.generateSequence(Person.SEQ_NAME));
            }
        }
    }
}
