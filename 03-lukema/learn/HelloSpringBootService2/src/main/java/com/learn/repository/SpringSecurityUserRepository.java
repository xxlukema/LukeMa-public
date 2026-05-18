package com.learn.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.entity.SpringSecurityUserEntity;


public interface SpringSecurityUserRepository
    extends JpaRepository<SpringSecurityUserEntity, Long> {

    SpringSecurityUserEntity findByUsername(String username);

}
