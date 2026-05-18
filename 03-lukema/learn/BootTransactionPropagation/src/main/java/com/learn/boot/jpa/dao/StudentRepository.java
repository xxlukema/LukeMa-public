package com.learn.boot.jpa.dao;


import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.learn.boot.jpa.entity.Student;
import com.learn.boot.jpa.exception.AppException;


/**
 * Tip 1:
 * Spring recommends that you only annotate concrete classes (and methods of concrete classes) with the 
 * @Transactional annotation, as opposed to annotating interfaces. You certainly can place the @Transactional 
 * annotation on an interface (or an interface method), but this works only as you would expect it to if you 
 * are using interface-based proxies. The fact that Java annotations are not inherited from interfaces means 
 * that if you are using class-based proxies (proxy-target-class="true") or the weaving-based aspect (mode="aspectj"), 
 * then the transaction settings are not recognized by the proxying and weaving infrastructure, and the object will 
 * not be wrapped in a transactional proxy, which would be decidedly bad.
 * 
 * Tip 2:
 * Method visibility and @Transactional
 * When using proxies, you should apply the @Transactional annotation only to methods with public visibility. If you 
 * do annotate protected, private or package-visible methods with the@Transactional annotation, no error is raised, 
 * but the annotated method does not exhibit the configured transactional settings. Consider the use of AspectJ 
 * (see below) if you need to annotate non-public methods.
 * 
 * Tip 3:
 * In proxy mode (which is the default), only external method calls coming in through the proxy are intercepted. 
 * This means that self-invocation, in effect, a method within the target object calling another method of the target 
 * object, will not lead to an actual transaction at runtime even if the invoked method is marked with @Transactional.
 * 
 * Tip 4:
 * Class level annotation applies to all public methods of the class. 
 * Method level annotaion of @Transactional overrides class level annotation.
 *
 */
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = { AppException.class })
@Repository
public interface StudentRepository
    extends JpaRepository<Student, Long> {

    static final Logger LOG = LogManager.getLogger();

    @Query("SELECT student FROM Student student WHERE student.name = (:name)")
    List<Student> findByName(@Param("name") String name);

    /**
     * Method level annotaion of @Transactional overrides class level annotation.
     */
    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = { AppException.class })
    default void transaction1()
        throws AppException {

        LOG.info("Enter 11111111111111111.");

    }

    /**
     * Method level annotaion of @Transactional overrides class level annotation.
     */
    @Transactional(propagation = Propagation.MANDATORY, isolation = Isolation.DEFAULT, readOnly = false, rollbackFor = { AppException.class })
    default void transaction2()
        throws AppException {

        LOG.info("Enter 22222222.");

        // throw new AppException();
    }

}
