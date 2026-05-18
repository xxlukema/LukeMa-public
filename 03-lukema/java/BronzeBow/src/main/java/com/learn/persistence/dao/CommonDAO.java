package com.learn.persistence.dao;


import com.learn.persistence.bean.BeanBase;


public interface CommonDAO {
    // public <T extends BeanBase> List<T> list(Class<T> clazz);

    public <T extends BeanBase> T save(T bean);

    public <T extends BeanBase> T update(T bean);

    public <T extends BeanBase> T saveOrUpdate(T bean);

    public <T extends BeanBase> void delete(T bean);
}
