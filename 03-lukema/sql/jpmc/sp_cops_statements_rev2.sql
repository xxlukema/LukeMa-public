USE OPICS
go
IF OBJECT_ID('dbo.sp_cops_statements_rev2') IS NOT NULL
BEGIN
    DROP PROCEDURE dbo.sp_cops_statements_rev2
    IF OBJECT_ID('dbo.sp_cops_statements_rev2') IS NOT NULL
        PRINT '<<< FAILED DROPPING PROCEDURE dbo.sp_cops_statements_rev2 >>>'
    ELSE
        PRINT '<<< DROPPED PROCEDURE dbo.sp_cops_statements_rev2 >>>'
END
go
create proc dbo.sp_cops_statements_rev2
                        (
                        @beg_date datetime,
                        @end_date datetime
                        )
as

set prefetch off 


/* Clear work tables from prior run */
    delete cops_statements_work
    delete cops_dep_wdl_work


/* Variable Declaration */
    declare @temp_date datetime
    declare @mod_beg_date datetime
    declare @mod_end_date datetime
                        

/* Create table to capture the interest start date associated with each business day of our records */
    create table #max_instrtdte
        (
        br              char(2),
        accountno       char(15),
        bus_date        datetime,
        max_instrtdte   datetime,
        acct_type       char(1)
        )


/* Create table to store all possible dates in our date range */
    create table #valid_dates
        (
        date_seq        int  not null,
        date_val        datetime not null
        )


/* Create table to capture the rate eff date associated with each business day of our records */
    create table #max_rt_effdate
        (
        br              char(2),
        ratecode        char(15),
        bus_date        datetime,
        max_rt_effdate  datetime
        )

/* Routine to populate all possible dates in our date range */
    select @mod_beg_date = dateadd(dd,-1,@beg_date)
    select @mod_end_date = dateadd(dd,1,@end_date)
    select @temp_date = @mod_beg_date

    begin transaction Step_01

    insert #valid_dates select 1, @mod_beg_date
    while @temp_date < @end_date
    BEGIN
      insert #valid_dates
        select max(date_seq) + 1,
               dateadd(dd, 1, @temp_date)
        from #valid_dates
      select @temp_date = dateadd(dd, 1, @temp_date)
    END

    commit transaction Step_01

/* Initial insert into cops_statements_work to capture the entire population of accounts
that we need to report on. */
    
    begin transaction Step_02

    insert cops_statements_work
        (br,
        bus_date,
        cust_short_name,
        cust_full_name1,
        cust_full_name2,
        accountno,
        basis,
        od_accountno,
        ratecode,
        spread_8,
        ccy,
        contact_name,
        cust_addr1,
        cust_addr2,
        cust_loc,
        cust_fax,
        beg_date,
        end_date,
        intcap,
        intpaycycle)
    select distinct
        ACCT.BR,
        #valid_dates.date_val,
        CUST.SN,
        CUST.CFN1,
        CUST.CFN2,
        ACCT.ACCOUNTNO,
        BASIS,
        ACCT.ODRECACCT,
        ACCT.RATECODE,
        ACCT.SPREAD_8,
        ACCT.CCY,
        CUSS.CONTACT,
        CUST.CA1,
        CUST.CA2,
        CUST.CA4,
        CUSS.LOCATION,
        @beg_date,
        @end_date,
        ACCT.INTCAP,
        ACCT.INTPAYCYCLE
    from
        ACCT, CUST, CUSS, #valid_dates
    where
        ACCT.CNO = CUST.CNO and
        ACCT.CNO = CUSS.CNO and
        ACCT.AL = 'L' and                                     
        ACCT.BR in ('83','84','85','86','87','88','89','90','91','92','93','94','95','96') and 
        ACCT.VERIND = '1' and
        (ACCT.CLOSEDATE is null or ACCT.CLOSEDATE > @mod_beg_date)

    commit transaction Step_02

/*************************************************************************
KGK - 10/14/2008 
Split ACDW select/insert into two sections to accomodate summing intcap moves and related 
intcap adjustments.  
Defaulted MOVENO and IOPER values, and added update to set DEPWITHIND after summation.
Begin [PPB-9602] changes
*************************************************************************/

    begin transaction Step_03

    insert cops_dep_wdl_work
    select
        ACDW.BR,
        ACDW.MOVENO,
        ACDW.ACCOUNTNO,
        ACDW.DEPWITHIND,
        ACDW.VDATE, 
        ACDW.CCYAMT,
        ACDW.INTFLAG,
        ACDW.IOPER
    from 
        ACDW, ACCT
    where
        ACDW.VDATE > @mod_beg_date and
        ACDW.VDATE < @mod_end_date and
        ACDW.INTFLAG = '0' and                          --non-interest moves
        ACDW.IOPER != 'BCNO' and
        ACDW.CCYAMT != 0.00 and
        ACDW.BR in ('83','84','85','86','87','88','89','90','91','92','93','94','95','96') and 
        ACDW.REVDATE is null and
        ACDW.VERIND = '1' and
        ACDW.BR = ACCT.BR and
        ACDW.ACCOUNTNO = ACCT.ACCOUNTNO and
        (ACCT.CLOSEDATE is null or ACCT.CLOSEDATE > @mod_beg_date)

    commit transaction Step_03
    
    begin transaction Step_04

    insert cops_dep_wdl_work
    select
        ACDW.BR,
        '',
        ACDW.ACCOUNTNO,
        'X',
        ACDW.VDATE, 
        case when ACCT.AL = 'L'
            then sum(ACDW.CCYAMT * (-1))
            else sum(ACDW.CCYAMT)
            end,
        ACDW.INTFLAG,
        'BCNP'
    from 
        ACDW, ACCT
    where
        ACDW.VDATE > @mod_beg_date and
        ACDW.VDATE < @mod_end_date and
        (ACDW.INTFLAG = '1' and ACDW.INTCAP = '1') and                            --interest cap moves         
        ACDW.IOPER != 'BCNO' and
        ACDW.CCYAMT != 0.00 and
        ACDW.BR in ('83','84','85','86','87','88','89','90','91','92','93','94','95','96') and 
        ACDW.REVDATE is null and
        ACDW.VERIND = '1' and
        ACDW.BR = ACCT.BR and
        ACDW.ACCOUNTNO = ACCT.ACCOUNTNO and
        (ACCT.CLOSEDATE is null or ACCT.CLOSEDATE > @mod_beg_date)
    group by 
        ACDW.BR, ACDW.ACCOUNTNO, ACDW.VDATE, ACCT.AL, ACDW.INTFLAG        
    
    commit transaction Step_04
    
    begin transaction Step_05

    update cops_dep_wdl_work
    set
        depwithind = case
            when ccyamt > 0 then 'W'
            else 'D'
            end
    where
        depwithind = 'X'
        and intflag = '1'
        and ioper = 'BCNP'
        and moveno = ''

    commit transaction Step_05

/*************************************************************************
 End [PPB-9602] changes
*************************************************************************/                     

/* 
Capture the maximum interest start date for each of our Account / bus day
combinatios -- this will tell us which ACBH record to use to get the balance for
that day.  
modified 3/27/03 JFW to only grab non-daily cap clients
*/
    /* Date insert 1 (liability records) */

    begin transaction Step_06

    insert #max_instrtdte
    select distinct
        cops_statements_work.br,
        cops_statements_work.accountno,
        cops_statements_work.bus_date,
        max(H.INSTRTDTE),
        'L'
    from
        cops_statements_work (index indx_cops_statements_work1), ACBH H
    where  
        cops_statements_work.accountno = H.ACCOUNTNO and
        cops_statements_work.bus_date >= H.INSTRTDTE and
        cops_statements_work.br = H.BR and
        cops_statements_work.intpaycycle !='D' 
    group by cops_statements_work.br, cops_statements_work.accountno, cops_statements_work.bus_date
 
    commit transaction Step_06

    begin transaction Step_07

    /* Date insert 1 (overdraft records) */
    insert #max_instrtdte
    select distinct
        cops_statements_work.br,
        cops_statements_work.accountno,
        cops_statements_work.bus_date,
        max(H.INSTRTDTE),
        'O'
    from
        cops_statements_work (index indx_cops_statements_work3), ACBH H
    where  
        cops_statements_work.od_accountno = H.ACCOUNTNO and
        cops_statements_work.bus_date >= H.INSTRTDTE and
        cops_statements_work.br = H.BR and
        cops_statements_work.intpaycycle !='D' 
    group by cops_statements_work.br, cops_statements_work.accountno, cops_statements_work.bus_date
 
    commit transaction Step_07

    begin transaction Step_08
 
    /* Date insert 2 (Liability Records)*/
    insert #max_instrtdte
    select distinct
        cops_statements_work.br,
        cops_statements_work.accountno,
        cops_statements_work.bus_date,
        max(H.INSTRTDTE),
        'L'
    from 
        cops_statements_work (index indx_cops_statements_work1), ACBH H
    where 
        NOT EXISTS
           (select * from ACBH C 
            where
                cops_statements_work.accountno = C.ACCOUNTNO and
                cops_statements_work.br = C.BR and 
                cops_statements_work.bus_date >= C.INSTRTDTE) and
        cops_statements_work.accountno = H.ACCOUNTNO and
        cops_statements_work.bus_date >= H.INSTRTDTE and
        cops_statements_work.br = H.BR and
        cops_statements_work.intpaycycle !='D' 
    group by cops_statements_work.br, cops_statements_work.accountno, cops_statements_work.bus_date
    
    commit transaction Step_08

    /* Date insert 2 (Overdraft Records)*/

    begin transaction Step_09

    insert #max_instrtdte
    select distinct
        cops_statements_work.br,
        cops_statements_work.accountno,
        cops_statements_work.bus_date,
        max(H.INSTRTDTE),
        'O'
    from 
        cops_statements_work (index indx_cops_statements_work3), ACBH H
    where 
        NOT EXISTS
           (select * from ACBH C 
            where
                cops_statements_work.od_accountno = C.ACCOUNTNO and
                cops_statements_work.br = C.BR and 
                cops_statements_work.bus_date >= C.INSTRTDTE) and
        cops_statements_work.od_accountno = H.ACCOUNTNO and
        cops_statements_work.bus_date >= H.INSTRTDTE and
        cops_statements_work.br = H.BR and
        cops_statements_work.intpaycycle !='D' 
    group by cops_statements_work.br, cops_statements_work.accountno, cops_statements_work.bus_date

    commit transaction Step_09

/*
Daily cap clients create a record each day even when the balance is zero that has
ORIGBALANCE = 0 ACBH.INTERESTAMT =0, BUT ACBH.INTENDDATE POPULATED.  Sue E. confirmed that on
daily cap clients, they will cap both the asset and liability daily so we should get
an ACBH record each day and use this balance. 3/14/03 JFW
*/

    begin transaction Step_10
    
    insert #max_instrtdte
    select distinct
        cops_statements_work.br,
        cops_statements_work.accountno,
        cops_statements_work.bus_date,
        max(ACBH.INSTRTDTE),
        'L'
    from 
        cops_statements_work (index indx_cops_statements_work1), ACBH
    where
        cops_statements_work.br = ACBH.BR and
        (cops_statements_work.accountno = ACBH.ACCOUNTNO or cops_statements_work.od_accountno = ACBH.ACCOUNTNO) and
        cops_statements_work.bus_date >= ACBH.INSTRTDTE and
        cops_statements_work.intcap = '1' and
        cops_statements_work.intpaycycle = 'D'       
    group by cops_statements_work.br, cops_statements_work.accountno, cops_statements_work.bus_date

    commit transaction Step_10
    
    begin transaction Step_11

    insert #max_instrtdte
    select distinct
        cops_statements_work.br,
        cops_statements_work.accountno,
        cops_statements_work.bus_date,
        max(ACBH.INSTRTDTE),
        'O'
    from 
        cops_statements_work (index indx_cops_statements_work1), ACBH
    where
        cops_statements_work.br = ACBH.BR and
        (cops_statements_work.accountno = ACBH.ACCOUNTNO or cops_statements_work.od_accountno = ACBH.ACCOUNTNO) and
        cops_statements_work.bus_date >= ACBH.INSTRTDTE and
        cops_statements_work.intcap = '1' and
        cops_statements_work.intpaycycle = 'D'       
    group by cops_statements_work.br, cops_statements_work.accountno, cops_statements_work.bus_date

    commit transaction Step_11

/* Apply dates to statement table */

    begin transaction Step_12

    update cops_statements_work
    set max_instrtdte_l = #max_instrtdte.max_instrtdte
    from 
        cops_statements_work (index indx_cops_statements_work1), #max_instrtdte
    where
        cops_statements_work.br = #max_instrtdte.br and
        cops_statements_work.accountno = #max_instrtdte.accountno and
        cops_statements_work.bus_date = #max_instrtdte.bus_date and
        #max_instrtdte.acct_type = 'L'

    commit transaction Step_12
    
    begin transaction Step_13

    update cops_statements_work
    set max_instrtdte_o = #max_instrtdte.max_instrtdte
    from 
        cops_statements_work (index indx_cops_statements_work1), #max_instrtdte
    where
        cops_statements_work.br = #max_instrtdte.br and
        cops_statements_work.accountno = #max_instrtdte.accountno and
        cops_statements_work.bus_date = #max_instrtdte.bus_date and
        #max_instrtdte.acct_type = 'O'

    commit transaction Step_13

/* Add balances from ACBH to work table */             

    begin transaction Step_14

    update cops_statements_work
    set
        s.eod_balance = isnull(ACBH1.ORIGBALANCE,0) - isnull(ACBH2.ORIGBALANCE,0)
    from
        cops_statements_work s LEFT OUTER JOIN ACBH ACBH1 on s.br = ACBH1.BR and
                                                       s.accountno = ACBH1.ACCOUNTNO and
                                                       s.max_instrtdte_l = ACBH1.INSTRTDTE
                               LEFT OUTER JOIN ACBH ACBH2 on s.br = ACBH2.BR and
                                                       s.od_accountno = ACBH2.ACCOUNTNO and
                                                       s.max_instrtdte_o = ACBH2.INSTRTDTE
 
    commit transaction Step_14

/* Apply zero to null values */

    begin transaction Step_15

    update cops_statements_work
    set eod_balance = 0.00
    where eod_balance is null

    commit transaction Step_15

/* Update beginning balance for each record.  The beginning balance is equal to
the prior days end of day balance. */

    begin transaction Step_16

    update cops_statements_work
    set A.beg_balance = B.eod_balance
    from cops_statements_work A (index indx_cops_statements_work1), cops_statements_work B (index indx_cops_statements_work1)
    where A.br = B.br and
          A.accountno = B.accountno and
          A.od_accountno = B.od_accountno and
          dateadd(dd, -1, A.bus_date) = B.bus_date and
          A.beg_balance is null

    commit transaction Step_16

/* Capture the maximum rate eff date for each of our Account / bus day
combinations -- this will tell us which RHIS record to use to get the
effective rate for that day */
/* Performance on this insert/select improved significantly (KGK 20051121) */
    create table #ratecode
            (
            br       char(2),
            ratecode char(15)
            )

    begin transaction Step_17

    insert into #ratecode        
    select distinct br, ratecode
    from
        cops_statements_work

    commit transaction Step_17

    begin transaction Step_18

    insert #max_rt_effdate
    select distinct
        RHIS.BR,
        #ratecode.ratecode,
        #valid_dates.date_val,
        max(RHIS.EFFDATE)
    from
        #ratecode, #valid_dates, RHIS
    where
        #ratecode.br = RHIS.BR and
        #ratecode.ratecode = RHIS.RATECODE and
        #valid_dates.date_val >= RHIS.EFFDATE and
        RHIS.VERIND = '1' and 
        RHIS.BR in ('83','84','85','86','87','88','89','90','91','92','93','94','95','96') 
    group by
        RHIS.BR, #ratecode.ratecode, #valid_dates.date_val
        
    commit transaction Step_18

    begin transaction Step_19

    update cops_statements_work
    set max_rt_effdate = #max_rt_effdate.max_rt_effdate
    from
        cops_statements_work, #max_rt_effdate
    where
        cops_statements_work.br = #max_rt_effdate.br and
        cops_statements_work.ratecode = #max_rt_effdate.ratecode and
        cops_statements_work.bus_date = #max_rt_effdate.bus_date

    commit transaction Step_19
    
    begin transaction Step_20

    update cops_statements_work
    set int_rate = case
        when (convert(numeric(18,8), RHIS.INTRATE) + cops_statements_work.spread_8) < 0 then 0
        else (convert(numeric(18,8), RHIS.INTRATE) + cops_statements_work.spread_8)
        end
    from 
        cops_statements_work, RHIS
    where
        cops_statements_work.br = RHIS.BR and
        cops_statements_work.ratecode = RHIS.RATECODE and
        cops_statements_work.max_rt_effdate = RHIS.EFFDATE

    commit transaction Step_20

/* Calculate Daily Interest Amounts */

    begin transaction Step_21

    update cops_statements_work
    set     dly_interest = (Case
                                When basis = 'A360' then convert(numeric(19,4),eod_balance * convert(numeric(18,12),(int_rate/100)/360))
                                When basis = 'A365' then convert(numeric(19,4),eod_balance * convert(numeric(18,12),(int_rate/100)/365))        
                                Else 0.0000
                            End)
    from cops_statements_work

    commit transaction Step_21

/* Final Select Statement */
    select cops_statements_work.br,
           cops_statements_work.basis,
           cops_statements_work.bus_date,
           cops_statements_work.cust_short_name,
           cops_statements_work.cust_full_name1,
           cops_statements_work.cust_full_name2,
           cops_statements_work.accountno,
           cops_statements_work.int_rate,
           cops_statements_work.beg_balance,
           cops_statements_work.eod_balance,
           cops_statements_work.dly_interest,
           cops_statements_work.ccy,
           cops_dep_wdl_work.ccyamt,
           cops_dep_wdl_work.moveno,
           cops_dep_wdl_work.intflag,           --PPB-9602
           cops_statements_work.accr_days,
           cops_statements_work.accr_int,
           cops_statements_work.nost_acct,
           cops_statements_work.contact_name,
           cops_statements_work.cust_addr1,
           cops_statements_work.cust_addr2,
           cops_statements_work.cust_loc,
           cops_statements_work.cust_fax,
           case when cops_statements_work.intcap = '1'
                   then cops_statements_work.intpaycycle
                   else 'N'
                   end as 'acct_type_ind'
    from cops_statements_work LEFT OUTER JOIN cops_dep_wdl_work on cops_statements_work.br = cops_dep_wdl_work.br
                                              and (cops_statements_work.accountno = cops_dep_wdl_work.accountno or
                                                   cops_statements_work.od_accountno = cops_dep_wdl_work.accountno)
                                              and cops_statements_work.bus_date = cops_dep_wdl_work.vdate    
    where
        bus_date >= @beg_date
    order by
        cops_statements_work.br,
        cops_statements_work.accountno,
        cops_statements_work.bus_date,
        cops_dep_wdl_work.moveno

set prefetch on 
return
go
EXEC sp_procxmode 'dbo.sp_cops_statements_rev2', 'unchained'
go
IF OBJECT_ID('dbo.sp_cops_statements_rev2') IS NOT NULL
    PRINT '<<< CREATED PROCEDURE dbo.sp_cops_statements_rev2 >>>'
ELSE
    PRINT '<<< FAILED CREATING PROCEDURE dbo.sp_cops_statements_rev2 >>>'
go
REVOKE EXECUTE ON dbo.sp_cops_statements_rev2 FROM OPXGRP
go
GRANT EXECUTE ON dbo.sp_cops_statements_rev2 TO OPXGRP
go
REVOKE EXECUTE ON dbo.sp_cops_statements_rev2 FROM coll_tech
go
GRANT EXECUTE ON dbo.sp_cops_statements_rev2 TO coll_tech
go
REVOKE EXECUTE ON dbo.sp_cops_statements_rev2 FROM opics_support
go
GRANT EXECUTE ON dbo.sp_cops_statements_rev2 TO opics_support
go
