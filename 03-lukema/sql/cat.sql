select a.ref, a.assoc_ref,
       r.des, a.se_group, a.min_freedom, a.max_freedom
from  oa_cat_assoc a, oa_se_info i, oa_se_ref r
where a.assoc_ref = i.ref
and   i.ref = r.ref
and   (a.min_freedom != '00' or 
       a.max_freedom != '00')
and   a.ref like '35HX3'
order by a.ref, a.se_group
/
