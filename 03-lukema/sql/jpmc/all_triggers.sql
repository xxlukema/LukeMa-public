/*
select RUN_NUMBER 
from cs_stream_queue
where RUN_NUMBER is not null
order by RUN_NUMBER desc
--where RUN_CATEGORY = 'INT_STMT'
*/


select * from all_triggers
where table_name like 'CS_%'






