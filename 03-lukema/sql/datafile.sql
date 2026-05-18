col "File Name" format a50
col "Tablespace" format a20
select d.name "File Name", t.name "Tablespace"
from v$datafile d, v$tablespace t
where t.ts# = d.ts#
/
