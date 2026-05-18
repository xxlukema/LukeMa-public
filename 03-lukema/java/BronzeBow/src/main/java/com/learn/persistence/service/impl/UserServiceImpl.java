package com.learn.persistence.service.impl;


import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.persistence.bean.User;
import com.learn.persistence.dao.UserDAO;
import com.learn.persistence.service.AppException;
import com.learn.persistence.service.UserService;


@Service("userService")
@Transactional
public class UserServiceImpl
    implements Serializable, UserService {

    private static final long serialVersionUID = 1L;

    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    public User getUserByEmail(String email)
        throws AppException {
        try {
            return userDAO.getUserByEmail(email);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

    public User getUserByUsername(String username)
        throws AppException {
        try {
            return userDAO.getUserByUsername(username);
        } catch (Exception e) {
            throw new AppException(e);
        }
    }

}
