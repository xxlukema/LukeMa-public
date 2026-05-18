package com.learn.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.entity.PortfolioUser;
import com.learn.exception.AppException;
import com.learn.repository.PortfolioUserRepository;
import com.learn.service.PortfolioUserService;


@Service("userService")
@Transactional
public class PortfolioUserServiceImpl
    implements PortfolioUserService {

    // private static final Logger log = LogManager.getLogger();

    @Autowired
    // @Qualifier("userDAO")
    private PortfolioUserRepository portfolioUserDao;

    @Override
    public PortfolioUser getUserByUsername(String username)
        throws AppException {
        try {
            return portfolioUserDao.getUserByUsername(username);
        } catch (Exception e) {
            throw new AppException("getUserByUsername() Exception", e);
        }
    }

    @Override
    public PortfolioUser getUserByEmail(String email)
        throws AppException {
        try {
            return portfolioUserDao.getUserByEmail(email);
        } catch (Exception e) {
            throw new AppException("getUserByEmail() Exception", e);
        }
    }

    @Override
    public PortfolioUser getUserByPhone(String phone)
        throws AppException {
        try {
            return portfolioUserDao.getUserByPhone(phone);
        } catch (Exception e) {
            throw new AppException("getUserByPhone() Exception", e);
        }
    }

    @Override
    @Transactional
    public PortfolioUser addUser(PortfolioUser user)
        throws AppException {
        try {
            String uname = user.getUsername();
            if (uname == null) {
                String email = user.getEmail();
                uname = email.substring(0, email.indexOf("@"));
                user.setUsername(uname);
            }

            return portfolioUserDao.save(user);
        } catch (Exception e) {
            throw new AppException("addUser() Exception", e);
        }
    }

    @Override
    @Transactional
    public void deleteUser(PortfolioUser user)
        throws AppException {
        try {
            portfolioUserDao.delete(user);
        } catch (Exception e) {
            throw new AppException("deleteUser() Exception", e);
        }
    }

}
