package com.learn.util;


public class JdbcConstants {

    public static final String SQL_SELECT_SYSDATE_NAMED_QUERY = "SELECT current_date as sys_datE, 'desc' as note where 100 != :num and current_date != :dat";

}
