package com.learn.shein.mongo.repository;


import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.learn.shein.mongo.model.SheinItem;


/**
 * Unnecessary `@Repository`
 */
public interface SheinItemRepository
    extends MongoRepository<SheinItem, Long> {

    List<SheinItem> findByTitle(String title);

    @Query(value = "{'status': {$ne : 'delete'}, 'sellerUsername': ?0}")
    List<SheinItem> findBySellerUsername(String sellerUsername);

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
    // @Query("select title from SheinItem where title LIKE %?%")
    // @Query(value = "{'title': {$regex : ?0, $options: 'm'}}")
    // @Query(value = "{'title': {$regex : ?0, $options: ''}}")
    @Query(value = "{'title': {$regex : ?0, $options: 'i'}}")
    List<SheinItem> findByNameContainingIgnoreCase(String title);

    /**
     * Not work!
     * @param title
     */
    @Query(value = "{'title': {$regex : :title, $options: 'i'}}")
    List<SheinItem> findByNameContainingIgnoreCase3(@Param("title") String title);
}
