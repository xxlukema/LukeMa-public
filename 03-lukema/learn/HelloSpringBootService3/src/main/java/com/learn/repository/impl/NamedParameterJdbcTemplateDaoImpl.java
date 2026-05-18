package com.learn.repository.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.learn.pojo.CurrentDatePojo;
import com.learn.repository.NamedParameterJdbcTemplateDao;
import com.learn.repository.rowmapper.SysdateRow;
import com.learn.repository.rowmapper.SysdateRowMapper;
import com.learn.util.EjbConstants;


@Repository("namedParameterJdbcTemplateDao")
public class NamedParameterJdbcTemplateDaoImpl
    implements NamedParameterJdbcTemplateDao {

    private static final Logger log = LogManager.getLogger();

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public CurrentDatePojo selectCurrentDateEntityManager() {
        log.info("Entering function.");

        TypedQuery<CurrentDatePojo> query = entityManager.createNamedQuery(EjbConstants.Select_Current_Date, CurrentDatePojo.class);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date before = calendar.getTime();

        query.setParameter("dat", before).setParameter("num", 1);

        return query.getSingleResult();
    }

    @Override
    public CurrentDatePojo selectCurrentDateJdbcTemplate() {

        log.info("Entering function.");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        Date before = calendar.getTime();

        Map<String, Object> namedParameters = new HashMap<>();
        namedParameters.put("num", 1);
        namedParameters.put("dat", before);

        List<SysdateRow> list = namedParameterJdbcTemplate.query(SysdateRowMapper.SQL_SELECT_SYSDATE_PARM, namedParameters,
                new SysdateRowMapper());

        log.debug("list.size() = {}", () -> list.size());

        for (SysdateRow row : list) {
            log.debug(row.getDate());
        }

        CurrentDatePojo currentDatePojo = new CurrentDatePojo();
        currentDatePojo.setDate(list.get(0).getDate());
        currentDatePojo.setNote("desc");

        return currentDatePojo;
    }
}
