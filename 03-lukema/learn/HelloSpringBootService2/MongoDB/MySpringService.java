package com.learn.pojo;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.dao.MongoDao;
import com.learn.data.User;


@Service
public class MySpringService {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private MongoDao mongoDao;

    public void findUsers() {
        List<User> listUsers = mongoDao.findAll(User.class);

        LOG.info("listUsers.size() = " + listUsers.size());
        listUsers.forEach((item) -> {
            LOG.info("Found: " + item);
        });
    }

    public void hello() {
        LOG.info("############## hello from MySpringService ##############");
    }

}
