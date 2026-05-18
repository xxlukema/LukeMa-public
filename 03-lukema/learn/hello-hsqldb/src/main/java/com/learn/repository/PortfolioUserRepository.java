package com.learn.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.learn.entity.PortfolioUser;


@Repository("portfolioUserDAO")
public interface PortfolioUserRepository
    extends JpaRepository<PortfolioUser, Long> {

    public PortfolioUser getUserByUsername(String username);

    public PortfolioUser getUserByEmail(String email);

    public PortfolioUser getUserByPhone(String phone);
}
