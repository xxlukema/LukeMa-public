select d.name "File Name", t.name "Tablespace"
from v$datafile d, v$tablespace t
where t.ts# = d.ts#
/
