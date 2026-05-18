package com.learn.mongo;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.learn.data.User;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:/SpringMVC-servlet.xml" })
public class MongoRepositoryTest {
    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    UserRepository userRepository;

    @Test
    public void testInsert()
        throws Exception {
        LOG.info("Begin Test");

        User user = new User();
        user.setName("Jon");
        userRepository.insert(user);

        LOG.info("Added user: " + user);

        LOG.info("End Test.");
    }

    @Test
    public void testSave()
        throws Exception {
        LOG.info("Begin Test");

        User user = new User();
        user.setName("Albert");
        userRepository.save(user);

        LOG.info("Saved user: " + user);

        LOG.info("End Test.");
    }

    @Test
    public void testFind()
        throws Exception {
        LOG.info("Begin Test");

        List<User> listUsers = userRepository.findAll();

        LOG.info("listUser.size() = " + listUsers.size());

        listUsers.forEach(item -> {
            LOG.info("Found user: " + item);
        });

        LOG.info("End Test.");
    }

}
