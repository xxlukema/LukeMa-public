package com.learn.shein.psql.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.shein.psql.entity.Country;


/**
 * Unnecessary `@Repository`
 */
public interface CountryRepository
    extends JpaRepository<Country, Long> {

    Country findByCode(String code);

}
