package com.learn.persistence.dao.impl;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.learn.persistence.bean.User;
import com.learn.persistence.dao.UserDAO;


@Repository("userDAO")
public class UserDAOImpl
    extends CommonDAOImpl
    implements UserDAO {

    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    public User getUserByUsername(String username)
        throws Exception {
        LOG.info("Entering function.");

        User user = new User();
        user.setUsername(username);

        Example example = Example.create(user);
        example.ignoreCase();

        @SuppressWarnings("unchecked")
        List<User> list = (List<User>) sessionFactory.getCurrentSession().getNamedQuery(User.FindUserByUsername).setString("username", username).list();

        LOG.info("list.size() = " + list.size());

        if (list.size() == 1) {
            user = list.get(0);
            return user;
        } else if (list.size() > 1) {
            String errorMessage = "User is not unique for username: " + username;
            LOG.error(errorMessage);
            throw new Exception(errorMessage);
        }

        LOG.info("No user found by username. Leaving function.");

        return null;
    }

    public User getUserByEmail(String email)
        throws Exception {
        LOG.info("Entering function.");

        User user = new User();
        user.setEmail(email);

        Example example = Example.create(user);
        example.ignoreCase();

        @SuppressWarnings("unchecked")
        List<User> list = (List<User>) sessionFactory.getCurrentSession().getNamedQuery(User.FindUserByEmail).setString("email", email).list();

        LOG.info("list.size() = " + list.size());

        if (list.size() == 1) {
            user = list.get(0);
            return user;
        } else if (list.size() > 1) {
            String errorMessage = "User is not unique for email: " + email;
            LOG.error(errorMessage);
            throw new Exception(errorMessage);
        }

        LOG.info("No user found by email. Leaving function.");

        return null;
    }
}
