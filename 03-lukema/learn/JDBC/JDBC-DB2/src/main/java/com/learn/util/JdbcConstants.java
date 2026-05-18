package com.learn.util;


public class JdbcConstants {

    // @formatter:off
    public static final String VaeDb[][] = { 
            // { "Msl",   "he3lxvd728", "50001", "MFASDB", "fird01", "phx#dq318_phx#dq318" }, // Verified
            // { "Psl",   "he3lxvd728", "50001", "MFASDB", "fird01", "phx#dq318_phx#dq318" }, // Verified
            // { "SfTds", "he3lxvd728", "50001", "SARDDB", "FIRD02", "phx#dq318_phx#dq318" }, // Verified
            // { "Srd",   "he3lxvd728", "50001", "MFASDB", "FIRD02", "phx#dq318_phx#dq318" }, // Verified
            // { "Tds",   "he3lxvd728", "50001", "MFASDB", "FIRD02", "phx#dq318_phx#dq318" }, // Verified Old Test
             { "Tds",   "he3qlxvddbs460", "50001", "SARDDB1", "FIRD01", "phx#dq318_phx#dq318" }, // Verified New Test
            // { "TdsGT", "he3lxvd728", "50001", "SARDDB", "FIRD02", "phx#dq318_phx#dq318" }, // Verified new
            // { "TdsT4", "he3lxvd728", "50001", "MFASDB", "fird01", "phx#dq318_phx#dq318" }, // Verified
            // { "TdsXa", "he3lxvd728", "50001", "MFASDB", "fird01", "phx#dq318_phx#dq318" }, // Verified
            // { "Ups",   "he3lxvd728", "50001", "MFASDB", "fird01", "phx#dq318_phx#dq318" }, // Verified
            // { "AmTds", "he3lxvd728", "50001", "MFASDB", "fird01", "phx#dq318_phx#dq318" }, // Verified
            // { "Asdb",  "he3lxvd728", "50001", "MFASDB", "fird01", "phx#dq318_phx#dq318" }, // Verified
             { "Cdw",   "he3qlxvddbs427",  "60000", "CDWDNEW", "fired1", "phx#dq318_phx#dq318" }, // Verified
            /**
             * Oracle
             */
            //  { "Crtd",  "he3lxvd730", "1521", "SRMD", "FIRD01", "Phxdq318" }, // Oracle / Wrong 
            //  { "Mps",   "he3lxvd728", "50001", "MFASDB", "fird01", "phx#dq318_phx#dq318" }, // Verified
        };

    // @formatter:on

    public static final int I = 0;

    public static final String USER = VaeDb[I][4];
    public static final String PASSWD = VaeDb[I][5];
    public static final String URL = "jdbc:db2://" + VaeDb[I][1] + ":" + VaeDb[I][2] + "/" + VaeDb[I][3];

    // public static final String USER = "FIRD02";
    // public static final String PASSWD = "phx#dq318_phx#dq318";
    // public static final String URL = "jdbc:db2://he3lxvd728:50001/SARDDB";

    public static final String SQL_SELECT_SYSDATE_AS = "SELECT CURRENT date as sysdate FROM SYSIBM.SYSDUMMY1";

    public static final String SQL_SELECT_SYSDATE = "SELECT CURRENT date as sysdate FROM SYSIBM.SYSDUMMY1";

    public static final String SQL_SELECT_SYSDATE_PARM = "SELECT CURRENT date as sysdate FROM SYSIBM.SYSDUMMY1 where 100 != :num and sysdate != :dat";

    public static final String SQL_PROC_EMP = "{ call emp_package.select_emp(?, ?) }";

}
