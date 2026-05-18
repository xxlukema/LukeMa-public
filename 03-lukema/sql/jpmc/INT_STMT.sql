
--select * from CS_INTSTMT_RESULT;



select * from cs_stream_queue
where run_category = 'INT_STMT'
order by cob_date desc;



/*
select * from CS_INTSTMT_STATUS
where STATEMENT_ID = 383318
or STATEMENT_ID = 512654;
*/


/*
select * from CS_INTSTMT_STATUS
where ACCOUNT_NUMBER = '33333301A';
*/


--select * from CS_INTSTMT_BALANCE where compound_ind = 'Y';
--select * from CS_INTSTMT_RAW_DATA where ACCOUNT_NUMBER = '33333301A' and (batch_number = 247170 or batch_number = 247160);


/*
select count(*) from OPS$COAST.CS_INTSTMT_BALANCE where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
select count(*) from OPS$COAST.CS_INTSTMT_CHARM_MOVE where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
select count(*) from OPS$COAST.CS_INTSTMT_CHARM_TRANSACTION where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
select count(*) from OPS$COAST.CS_INTSTMT_CONTACT where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
select count(*) from OPS$COAST.CS_INTSTMT_MOVE where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
select count(*) from OPS$COAST.CS_INTSTMT_RAW_DATA where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
select count(*) from OPS$COAST.CS_INTSTMT_RESULT where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
select count(*) from OPS$COAST.CS_INTSTMT_STATUS where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
--BATCH_NUMBER = 247129;
*/

/*
delete from OPS$COAST.CS_INTSTMT_BALANCE where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
delete from OPS$COAST.CS_INTSTMT_CHARM_MOVE where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
delete from OPS$COAST.CS_INTSTMT_CHARM_TRANSACTION where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
delete from OPS$COAST.CS_INTSTMT_CONTACT where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
delete from OPS$COAST.CS_INTSTMT_MOVE where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
delete from OPS$COAST.CS_INTSTMT_RAW_DATA where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
delete from OPS$COAST.CS_INTSTMT_RESULT where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
delete from OPS$COAST.CS_INTSTMT_STATUS where UPDATE_DATETIME > to_date('2011-01-25', 'yyyy-mm-dd');
commit;
*/


/*
select * 
from CS_INTSTMT_RES_MOVE_BO
where STATEMENT_ID = 548161;
*/


/*
select * 
from CS_INTSTMT_RES_MOVE_BO
where STATEMENT_ID = 383318
or STATEMENT_ID = 512654;
*/


/*
select * from CS_INTSTMT_BALANCE
where STATEMENT_ID = 383318
or STATEMENT_ID = 512654;
*/


/*
select * from CS_INTSTMT_RESULT
where STATEMENT_ID = 383318
or STATEMENT_ID = 512654;
*/


/*
update CS_INTSTMT_RESULT
set ASSET_DLY_INT = 1234,
    LIAB_DLY_INT = 34567
where STATEMENT_ID = 383318;
*/


/*
update CS_INTSTMT_BALANCE
set ASSET_DLY_INT = 1234,
    LIAB_DLY_INT = 34567
where STATEMENT_ID = 383318;
*/


/*
update CS_INTSTMT_RESULT
set ASSET_DLY_INT = 1234,
    LIAB_DLY_INT = 34567,
    COMPOUND_IND = 'Y'
where STATEMENT_ID = 512654;
*/


/*
update CS_INTSTMT_BALANCE
set ASSET_DLY_INT = 1234,
    LIAB_DLY_INT = 34567,
    COMPOUND_IND = 'Y'
where STATEMENT_ID = 512654;
*/


/*
select * 
from CS_INTSTMT_BALANCE
where LIAB_DLY_INT > 0
or ASSET_DLY_INT > 0
*/



