package com.learn.mongo;


import java.math.BigInteger;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.learn.data.User;


@EnableMongoRepositories(basePackages = "org.baeldung.repository")
public interface UserRepository
    extends MongoRepository<User, BigInteger> {

}
