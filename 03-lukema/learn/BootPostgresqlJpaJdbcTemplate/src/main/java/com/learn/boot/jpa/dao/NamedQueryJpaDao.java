package com.learn.boot.jpa.dao;


import java.util.Map;

import com.learn.boot.jpa.pojo.SysdatePojo;


public interface NamedQueryJpaDao {

    SysdatePojo selectCurrentDateJpa(Map<String, Object> namedParameters);

}
