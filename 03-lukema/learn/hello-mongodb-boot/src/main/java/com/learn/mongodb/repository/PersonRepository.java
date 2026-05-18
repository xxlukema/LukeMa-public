package com.learn.mongodb.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.learn.mongodb.model.Person;


/**
 * Unnecessary `@Repository`
 */
public interface PersonRepository
    extends MongoRepository<Person, Long> {

    List<Person> findByName(String name);

    /**
     * https://docs.mongodb.com/manual/reference/operator/query/regex/
     *
     * { <field>: { $regex: /pattern/, $options: '<options>' } }
     * { <field>: { $regex: 'pattern', $options: '<options>' } }
     * { <field>: { $regex: /pattern/<options> } }
     *
     * $options:
     *    i --- case insensitive
     *    m --- For patterns that include anchors (i.e. ^ for the start, $ for the end), match at the beginning or end of each line for
     *          strings with multiline values. Without this option, these anchors match at beginning or end of the string.
     *    x --- ignore whitespaces
     *    s --- Allows the dot character (i.e. .) to match all characters including newline characters.
     *
     */
    // @Query("select person from Person where name LIKE %?%")
    // @Query(value = "{'name': {$regex : ?0, $options: 'm'}}")
    // @Query(value = "{'name': {$regex : ?0, $options: ''}}")
    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<Person> findByNameContainingIgnoreCase(String name);

    /**
     * Not work!
     * @param name
     */
    @Query(value = "{'name': {$regex : :name, $options: 'i'}}")
    List<Person> findByNameContainingIgnoreCase3(@Param("name") String name);

}
