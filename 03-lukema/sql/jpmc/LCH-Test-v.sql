
/*
update CS_CH_FEED
set SOURCE_LOCATION = 'Jpc(mbr)/SwapClear/Risk/SwapClear%20Combined%20Margins/Initial%20Margin/REP00050c%20-%20SwapClear%20Initial%20Margin_%201.TXT'
where SOURCE_FEED_NAME = 'LCHEMEAIA';


update CS_CH_FEED
set SOURCE_LOCATION = 'Jpc(mbr)/SwapClear/Risk/SwapClear%20Combined%20Margins/Cash%20Settlement/OTC/OTC%20NPVs/REP00072c%20-%20Cash%20Flow%20and%20Trade%20Level%20NPV_%201.TXT'
where SOURCE_FEED_NAME = 'LCHEMEAMTM';
*/


/*
update CS_CH_FEED
set LCH_SITE = '194.62.172.34:444/reporting',
LCH_USER = 'STSTJPC3456',
LCH_PWD = 'MORGAN3456'
where SOURCE_FEED_NAME = 'LCHEMEAIA'
or SOURCE_FEED_NAME = 'LCHEMEAMTM';
*/


/*
update CS_CH_FEED
set LCH_SITE = '194.62.172.34:444/reporting',
LCH_USER = 'STSTJPCCLEAR7',
LCH_PWD = 'JPMRCCMO'
where SOURCE_FEED_NAME = 'LCHEMEAIA'
or SOURCE_FEED_NAME = 'LCHEMEAMTM';
*/



/*
update CS_CH_FEED
set LCH_SITE = '194.62.172.34:444/reporting',
LCH_USER = 'STSTJPCCLEAR7',
LCH_PWD = 'JPMRCCMO'
where SOURCE_FEED_NAME = 'LCHEMEAIAJPMSL'
or SOURCE_FEED_NAME = 'LCHEMEAMTMJPMSL';
*/


/*
update CS_CH_FEED
set LCH_SITE = '194.62.172.34:444/reporting',
LCH_USER = 'STSTJPCCLEAR7',
LCH_PWD = 'JPMRCCMO'
where SOURCE_FEED_NAME = 'LCHEMEAIAJPMSL'
or SOURCE_FEED_NAME = 'LCHEMEAMTMJPMSL';
*/


/*
select * 
from CS_STG_LCH_IA
where SOURCE_FEED_NAME = 'LCHEMEAIA'
and rownum < 10
order by COB_DATE desc;



select * 
from CS_CH_IA
where SOURCE_FEED_NAME = 'LCHEMEAIA'
and rownum < 10
order by COB_DATE desc;



select * 
from CS_STG_LCH_MTM
where SOURCE_FEED_NAME = 'LCHEMEAMTM'
and rownum < 10
order by COB_DATE desc;


select * 
from CS_CH_MTM
where SOURCE_FEED_NAME = 'LCHEMEAMTM'
and rownum < 10
order by COB_DATE desc;
*/



SELECT PACCT, PATYPE, PCURSY, PSTYPE, PCUSIP, PEXPDT, PCLOSE, PQTY, PMULTF, PCLOSE, PQTY*PMULTF*PCLOSE FACE_VALUE, PMKVAL
from IAPOSFF_1
where PACCT = 'TST03' and PFIRM = 'M'
and substring(PTRACE,5,1)<> '*'










