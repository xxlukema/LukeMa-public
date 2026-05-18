package com.learn.daoFactory;


public enum DAOFactoryType
{
   MySqlDAOFactory("MySQL"), OracleDAOFactory("Oracle"), SqlServerDAOFactory("MS SQL Server");

   private String desc = null;

   private DAOFactoryType(String desc)
   {
      this.desc = desc;
   }

   public String getDesc()
   {
      return desc;
   }
}
