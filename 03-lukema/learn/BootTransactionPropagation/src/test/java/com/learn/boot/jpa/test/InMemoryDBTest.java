package com.learn.boot.jpa.test;


import java.util.List;
import java.util.Optional;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import com.learn.boot.config.BootAppConfig;
import com.learn.boot.jpa.dao.StudentRepository;
import com.learn.boot.jpa.entity.Student;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BootAppConfig.class }, loader = AnnotationConfigContextLoader.class)
@Transactional
public class InMemoryDBTest {

    private static final Logger LOG = LogManager.getLogger();

    @Resource
    private StudentRepository studentRepository;

    @Test
    public void testItWorks() {

        LOG.info("Begin Test");

        Student student = new Student(1, "john");
        studentRepository.save(student);

        Optional<Student> student2 = studentRepository.findById(1L);

        student2.ifPresent(LOG::info);

        List<Student> studentList = studentRepository.findByName("john");
        LOG.info("john list size: " + studentList.size());
        studentList.forEach(LOG::info);

        studentList = studentRepository.findByName("luke");
        LOG.info("luke list size: " + studentList.size());
        studentList.forEach(LOG::info);

        LOG.info("End Test.");
    }

}
