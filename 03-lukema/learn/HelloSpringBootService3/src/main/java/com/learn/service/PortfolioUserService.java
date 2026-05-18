package com.learn.service;


import org.springframework.stereotype.Service;

import com.learn.entity.PortfolioUser;
import com.learn.exception.AppException;


@Service
public interface PortfolioUserService {
    public PortfolioUser getUserByUsername(String username)
        throws AppException;

    public PortfolioUser getUserByEmail(String email)
        throws AppException;

    public PortfolioUser getUserByPhone(String phone)
        throws AppException;

    public PortfolioUser addUser(PortfolioUser user)
        throws AppException;

    public void deleteUser(PortfolioUser user)
        throws AppException;
}
