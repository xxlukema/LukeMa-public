select a.ref, a.assoc_ref,
       r.des, a.se_group, a.freedom
from cat_assoc a, se_info i, se_ref r
where a.assoc_ref = i.ref
and   i.ref = r.ref
and   a.freedom != '00'
and   (
         a.ref like '16HX%'
         or
         a.ref like '16HR%'
         or
         a.ref like '16PR%'
         or
         a.ref like '16CP%'
      )
order by a.ref, a.se_group
/
