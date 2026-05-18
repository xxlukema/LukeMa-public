package com.learn.shein.psql.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.shein.psql.entity.SheinProductImage;


/**
 * Unnecessary `@Repository`
 */
public interface SheinProductImageRepository
    extends JpaRepository<SheinProductImage, Long> {

}
