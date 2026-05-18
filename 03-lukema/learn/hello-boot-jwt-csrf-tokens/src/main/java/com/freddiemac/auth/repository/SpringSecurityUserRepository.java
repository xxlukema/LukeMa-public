package com.freddiemac.auth.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.freddiemac.auth.entity.SpringSecurityUserEntity;


@Repository
public interface SpringSecurityUserRepository
    extends JpaRepository<SpringSecurityUserEntity, Long> {

    SpringSecurityUserEntity findByUsername(String username);

}
