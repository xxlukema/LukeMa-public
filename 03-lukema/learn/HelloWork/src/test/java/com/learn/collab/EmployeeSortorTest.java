package com.learn.collab;


import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Test;


public class EmployeeSortorTest {

    private static final Logger LOG = LogManager.getLogger();

    @Test
    public void testByName() {

        LOG.info("Start Test.");

        List<Employee> list = new ArrayList<>();
        Employee e1 = new Employee();
        e1.setId(1L);
        e1.setName("Luke");
        list.add(e1);

        e1 = new Employee();
        e1.setId(2L);
        e1.setName("John");
        list.add(e1);
        
        e1 = new Employee();
        e1.setId(3L);
        e1.setName("Albert");
        list.add(e1);

        EmployeeSortor.sortByName(list);

        for (Employee e : list) {
            LOG.info(e.toString());
        }

        LOG.info("End Test.");

    }
    
    @Test
    public void testByAge() {

        LOG.info("Start Test.");

        List<Employee> list = new ArrayList<>();
        Employee e1 = new Employee();
        e1.setId(1L);
        e1.setName("Luke");
        e1.setAge(40);
        list.add(e1);

        e1 = new Employee();
        e1.setId(2L);
        e1.setName("John");
        e1.setAge(2);
        list.add(e1);
        
        e1 = new Employee();
        e1.setId(3L);
        e1.setName("Albert");
        e1.setAge(50);
        list.add(e1);

        EmployeeSortor.sortByAge(list);

        for (Employee e : list) {
            LOG.info(e.toString());
        }

        LOG.info("End Test.");

    }


}
