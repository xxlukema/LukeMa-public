

select *
from CS_CH_IA
where SOURCE_FEED_NAME = 'LCHEMEAIAJPMSL'
and COB_DATE = to_date('2011-01-26', 'yyyy-mm-dd')
and CH_ACCT_NBR = 'JPEUCCTEST2B';



/*
select *
from CS_CH_IA
where SOURCE_FEED_NAME = 'LCHEMEAIAJPMSL'
and COB_DATE = date('2011-01-26')
and CH_ACCT_NBR = 'JPEUCCTEST2B';
*/


/*
create table CS_CH_IA
(
   SOURCE_FEED_NAME    varchar(20),
   COB_DATE            date,
   CH_ACCT_NBR         varchar(20),
   update_datetime     date
);
*/


/*
insert into CS_CH_IA
(
SOURCE_FEED_NAME,
COB_DATE,
CH_ACCT_NBR,
update_datetime
)
values
(
'LCHEMEAIAJPMSL',
date('2011-01-26'),
'JPEUCCTEST2B',
date('2011-02-06')
);
*/
