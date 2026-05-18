package com.learn.shein.psql.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.shein.psql.entity.SheinProduct;


/**
 * Unnecessary `@Repository`
 */
public interface SheinProductRepository
    extends JpaRepository<SheinProduct, Long> {
}
