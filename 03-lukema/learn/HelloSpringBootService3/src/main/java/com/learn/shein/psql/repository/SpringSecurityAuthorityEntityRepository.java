package com.learn.shein.psql.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.learn.entity.SpringSecurityAuthorityEntity;


public interface SpringSecurityAuthorityEntityRepository

    extends JpaRepository<SpringSecurityAuthorityEntity, Long> {

    public SpringSecurityAuthorityEntity findByUsername(String username);

    public SpringSecurityAuthorityEntity findByAuthority(String authority);

}
