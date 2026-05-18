package com.learn.common.dao;


import java.util.List;

import com.learn.bean.BeanBase;


public interface CommonDAO
{
   public <T extends BeanBase> List<T> list(Class<T> clazz);

   public <T extends BeanBase> T saveOrUpdate(T bean);

}
