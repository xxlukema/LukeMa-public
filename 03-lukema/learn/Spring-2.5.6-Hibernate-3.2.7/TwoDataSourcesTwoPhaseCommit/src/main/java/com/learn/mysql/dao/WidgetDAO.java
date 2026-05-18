package com.learn.mysql.dao;


import java.util.List;

import com.learn.bean.mysql.MySQLObject;
import com.learn.bean.mysql.Widget;
import com.learn.common.dao.CommonDAO;


public interface WidgetDAO
   extends CommonDAO
{
   public List<Widget> list();

   public <T extends MySQLObject> T saveOrUpdate(T bean);
}
