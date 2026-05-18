select a.rowid, a.ref, 
       a.assoc_ref, 
       r.des, a.as_charge, 
       a.se_group, a.freedom
from cat_assoc a, 
     se_info i, se_ref r
where a.assoc_ref = i.ref
and   i.ref = r.ref
and   a.freedom != '00'
and   a.ref like '16PR2'
order by a.ref, a.se_group
/
