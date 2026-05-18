package com.learn.shein.psql.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.entity.SpringSecurityUserEntity;


/**
 * Unnecessary `@Repository`
 */
public interface SpringSecurityUserEntityRepository
    extends JpaRepository<SpringSecurityUserEntity, Long> {

    public SpringSecurityUserEntity findByUsername(String username);

    public SpringSecurityUserEntity findByPhone(String phoneString);
}
