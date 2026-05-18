--exec as user system 
col "File Name" format a40
col "Tablespace" format a20
select d.name "File Name", d.bytes, t.name "Tablespace"
from v$datafile d, v$tablespace t
where t.ts# = d.ts#
/
