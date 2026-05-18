package com.learn.service.impl;


import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.learn.bean.BeanBase;
import com.learn.bean.mysql.Widget;
import com.learn.bean.oracle.Person;
import com.learn.common.dao.CommonDAO;
import com.learn.service.AppException;
import com.learn.service.MyService;


@Service("myService")
public class MyServiceImpl
    implements MyService {
    private static final Logger LOG = Logger.getLogger(MyServiceImpl.class);

    @Autowired
    @Qualifier("personDAO")
    private CommonDAO personDAO;

    @Autowired
    @Qualifier("widgetDAO")
    private CommonDAO widgetDAO;

    public CommonDAO getPersonDAO() {
        return personDAO;
    }

    public void setPersonDAO(CommonDAO personDAO) {
        this.personDAO = personDAO;
    }

    public void setWidgetDAO(CommonDAO widgetDAO) {
        this.widgetDAO = widgetDAO;
    }

    public CommonDAO getWidgetDAO() {
        return widgetDAO;
    }

    public void list()
        throws Exception {
        List<Person> people = getPersonDAO().list(Person.class);

        LOG.info("people.size() = " + people.size());

        for (Person person : people) {
            LOG.info("Person name = " + person.getName());
        }

        List<Widget> widgets = getWidgetDAO().list(Widget.class);

        LOG.info("widgets.size() = " + widgets.size());

        for (Widget widget : widgets) {
            LOG.info("Widget name = " + widget.getName());
        }
    }

    @Transactional(rollbackFor = AppException.class)
    public void saveObjects(BeanBase oracleObject, BeanBase mySQLObject)
        throws Exception {
        // Oracle Person
        getPersonDAO().saveOrUpdate(oracleObject);

        // MySQL Widget
        getWidgetDAO().saveOrUpdate(mySQLObject);

        throw new Exception("This will not affact transaction.");
    }

    @Transactional(rollbackFor = AppException.class)
    public void saveObjectsRollback(BeanBase oracleObject, BeanBase mySQLObject)
        throws Exception {
        // Oracle Person
        getPersonDAO().saveOrUpdate(oracleObject);

        // MySQL Widget
        getWidgetDAO().saveOrUpdate(mySQLObject);

        throw new AppException("This will cause Rollback");
    }
}
