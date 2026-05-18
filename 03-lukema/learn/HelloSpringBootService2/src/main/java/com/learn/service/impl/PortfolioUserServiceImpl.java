package com.learn.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.dao.PortfolioUserDAO;
import com.learn.entity.PortfolioUser;
import com.learn.exception.AppException;
import com.learn.service.PortfolioUserService;


@Service("userService")
@Transactional
public class PortfolioUserServiceImpl
    implements PortfolioUserService {

    // private static final Logger LOG = LogManager.getLogger();

    @Autowired
    // @Qualifier("userDAO")
    private PortfolioUserDAO portfolioUserDAO;

    @Override
    public PortfolioUser getUserByUsername(String username)
        throws AppException {
        return portfolioUserDAO.getUserByUsername(username);
    }

    @Override
    public PortfolioUser getUserByEmail(String email)
        throws AppException {
        return portfolioUserDAO.getUserByEmail(email);
    }

    @Override
    public PortfolioUser getUserByPhone(String phone)
        throws AppException {
        return portfolioUserDAO.getUserByPhone(phone);
    }

    @Override
    @Transactional
    public PortfolioUser addUser(PortfolioUser user)
        throws AppException {

        String uname = user.getUsername();
        if (uname == null) {
            String email = user.getEmail();
            uname = email.substring(0, email.indexOf("@"));
            user.setUsername(uname);
        }

        return portfolioUserDAO.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(PortfolioUser user)
        throws AppException {

        portfolioUserDAO.delete(user);
    }

}
