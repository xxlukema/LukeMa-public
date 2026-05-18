
/*
select *
from CS_INTSTMT_STATUS
where CUMULATIVE_INTEREST is null
order by UPDATE_DATETIME asc;
*/


/*
SELECT distinct account_number
FROM ops$coast.cs_intstmt_raw_data
ORDER BY account_number desc;
*/


SELECT ACCOUNT_NUMBER, MOVE_AMOUNT, DATE_TIME
FROM ops$coast.cs_intstmt_raw_data
where ACCOUNT_NUMBER = 'PIMCOZZ7A'
and MOVE_AMOUNT != 0;


/*
select * from CS_INTSTMT_RAW_DATA
where ACCOUNT_NUMBER = '33333301A'
and UPDATE_DATETIME > to_date('2011-01-01', 'yyyy-mm-dd');
*/



