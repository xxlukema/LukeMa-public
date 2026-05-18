package com.learn.rest.resource.test.new_junit;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.learn.conf.test.BootMvcTestConfig;


// @ExtendWith(SpringExtension.class)
@SpringBootTest
@ContextConfiguration(classes = BootMvcTestConfig.class)
public class HelloSpringBootMainApplicationTest {

    @Test
    public void contextLoads() {
    }

}
