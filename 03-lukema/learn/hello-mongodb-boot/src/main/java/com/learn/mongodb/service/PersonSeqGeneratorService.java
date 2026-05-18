package com.learn.mongodb.service;


import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.Objects;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.learn.mongodb.model.PersonSeq;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class PersonSeqGeneratorService {

    private final MongoOperations mongoOperations;

    /**
     * Implicit constructor injection
     */
    // Auto generated constructor by lombok

    public long generateSequence(String seqName) {

        // @formatter:off
        PersonSeq counter = mongoOperations.findAndModify(
                query(where("_id").is(seqName)),
                new Update().inc("seq",1),
                options().returnNew(true).upsert(true),
                PersonSeq.class);

        return Objects.isNull(counter) ? 1 : counter.getSeq();
        // @formatter:on
    }

}
