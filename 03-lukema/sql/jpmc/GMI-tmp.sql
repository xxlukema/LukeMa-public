

select 
    RUN_CATEGORY,
    RUN_NUMBER,   
    SEQUENCE,
    COB_DATE,
    STATUS,
    ARG1,
    ARG2
from CS_STREAM_QUEUE
where RUN_CATEGORY like 'INT%'
order by 1, 2, 3;


--select * from CV_LOAD_STATUS where SOURCE_FEED_NAME like 'GMI%'


/*
select distinct
    STATUS
from CS_STREAM_QUEUE;
*/

