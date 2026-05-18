

-- 1. Access_Records Cleanup
create or replace procedure Access_Records_Cleanup_Proc
(
   RowNumToKeep      number
)
is

minIdToKeep Access_Records.id%type;

begin

select min(id) 
into minIdToKeep
from (
   select id 
   from Access_Records 
   where rownum < RowNumToKeep 
   order by id desc
);

delete from Access_Records
where id < minIdToKeep;

end;
/

-- 2. Access_Alarms Cleanup
create or replace procedure Access_Alarms_Cleanup_Proc
(
   RowNumToKeep      number
)
is

minIdToKeep Access_Alarms.id%type;

begin

select min(id) 
into minIdToKeep
from (
   select id 
   from Access_Alarms 
   where rownum < RowNumToKeep 
   order by id desc
);

delete from Access_Alarms
where id < minIdToKeep;

end;
/


-- Test Proc
declare
   rownums number;
begin
   rownums := 10;
   Access_Records_Cleanup_Proc(rownums);
end;
/




