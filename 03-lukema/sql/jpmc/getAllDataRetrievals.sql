
SELECT arg1, queue.status, start_datetime, end_datetime, queue.run_number, 
   (
      SELECT count(*) 
      FROM cs_intstmt_status intstmt 
      WHERE intstmt.BATCH_NUMBER = queue.run_number 
   ) AS account_count 
FROM cs_stream_queue queue, cs_run_status run 
WHERE queue.run_category = 'INT_STMT'
AND run.run_number(+) = queue.run_number

--and queue.run_number = 245288


/*
select * 
from cs_stream_queue 
where run_category='INT_STMT'
order by SEQUENCE desc
*/

