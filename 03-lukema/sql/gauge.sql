select 
       r.id, 
       to_char(r.INSERT_DATE, 'dd-mm-yy hh:mi:ss') r_insert_date, 
   --    r.MANUFACTURER, 
   --    r.MATERIAL_TYPE,
       to_char(f.INSERT_DATE, 'dd-mm-yy hh:mi:ss') f_insert_date, 
       f.ID,
       f.FACILITY_ID
from recorder r, facility_status f
where r.MANUFACTURER like '%_luke'
and   r.MATERIAL_TYPE like '%_luke'
and   f.FACILITY_ID = r.id
/
