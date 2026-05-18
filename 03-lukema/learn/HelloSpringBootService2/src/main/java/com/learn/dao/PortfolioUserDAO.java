package com.learn.dao;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.learn.entity.PortfolioUser;


@Repository("portfolioUserDAO")
public interface PortfolioUserDAO
    extends CrudRepository<PortfolioUser, Long> {

    public PortfolioUser getUserByUsername(String username);

    public PortfolioUser getUserByEmail(String email);

    public PortfolioUser getUserByPhone(String phone);
}
